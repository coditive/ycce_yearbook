package com.syrous.ycceyearbook.presenter

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.syrous.ycceyearbook.flux.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface PdfRendererView {
    val coroutineScope: CoroutineScope
    val pdfFile: SharedFlow<File>
    val showPrevious: SharedFlow<Boolean>
    val showNext: SharedFlow<Boolean>
    fun showPdfBitmap(pdfBitmap: SharedFlow<Bitmap>)
    fun showPageInfo(pageInfo: SharedFlow<Pair<Int, Int>>)
    fun enableNext(next: SharedFlow<Boolean>)
    fun enablePrevious(previous: SharedFlow<Boolean>)
}

class PdfRendererPresenter(
    private val view: PdfRendererView
): Presenter(){

    private var useInstantExecutor: Boolean = false

    private val job = Job()
    private val executor = if (useInstantExecutor) {
        Executor { it.run() }
    } else {
        Executors.newSingleThreadExecutor()
    }
    private val scope = CoroutineScope(executor.asCoroutineDispatcher() + job)
    private lateinit var pdfFile: File
    private var fileDescriptor: ParcelFileDescriptor? = null
    private var pdfRenderer: PdfRenderer? = null
    private var currentPage: PdfRenderer.Page? = null
    private var cleared = false
    private val pdfBitmap = MutableSharedFlow<Bitmap>()
    private val pageInfo = MutableSharedFlow<Pair<Int, Int>>()
    private val previousEnabled = MutableSharedFlow<Boolean>()
    private val nextEnabled = MutableSharedFlow<Boolean>()

    override fun onViewReady() {
        super.onViewReady()

        view.coroutineScope.launch {
            launch {
                view.pdfFile.collect {
                    pdfFile = it
                    launchPdfRendererThread()
                }
            }

            launch {
                view.showNext.collect {
                    showNext()
                }
            }

            launch {
                view.showPrevious.collect {
                    showPrevious()
                }
            }
        }

        view.showPdfBitmap(pdfBitmap)
        view.enableNext(nextEnabled)
        view.enablePrevious(previousEnabled)
        view.showPageInfo(pageInfo)
    }

    private fun launchPdfRendererThread() {
        scope.launch {
            openPdfRenderer()
            showPage(0)
            if (cleared) {
                closePdfRenderer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.launch {
            closePdfRenderer()
            cleared = true
            job.cancel()
        }
    }

    private fun openPdfRenderer() {
            Timber.d("File status : ${pdfFile.name}")

            if (!pdfFile.exists()) {
                Timber.d("File Not Found !!!")
            } else {
                fileDescriptor = ParcelFileDescriptor.open(pdfFile,
                    ParcelFileDescriptor.MODE_READ_ONLY).also {
                    pdfRenderer = PdfRenderer(it)
                }
            }
    }


    private fun showPrevious() {
        scope.launch {
            currentPage?.let { page ->
                if (page.index > 0) {
                    showPage(page.index - 1)
                }
            }
        }
    }


    private fun showNext() {
        scope.launch {
            pdfRenderer?.let { renderer ->
                currentPage?.let { page ->
                    if (page.index + 1 < renderer.pageCount) {
                        showPage(page.index + 1)
                    }
                }
            }
        }
    }


    private suspend fun showPage(index: Int) {
        // Make sure to close the current page before opening another one.
        currentPage?.let { page ->
            currentPage = null
            page.close()
        }
        pdfRenderer?.let { renderer ->
            // Use `openPage` to open a specific page in PDF.
            val page = renderer.openPage(index).also {
                currentPage = it
            }
            // Important: the destination bitmap must be ARGB (not RGB).
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            // Here, we render the page onto the Bitmap.
            // To render a portion of the page, use the second and third parameter. Pass nulls to get
            // the default result.
            // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            Timber.d("Bitmap size : ${bitmap.byteCount}")
            pdfBitmap.emit(bitmap)
            val count = renderer.pageCount
            pageInfo.emit(index to count)
            previousEnabled.emit(index > 0)
            nextEnabled.emit(index + 1 < count)
        }
    }

    private fun closePdfRenderer() {
        currentPage?.close()
        pdfRenderer?.close()
        fileDescriptor?.close()
    }

}