
package com.syrous.ycceyearbook.ui.pdf_screen

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.coroutineScope
import com.syrous.ycceyearbook.databinding.FragmentPdfRendererBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.presenter.PdfRendererPresenter
import com.syrous.ycceyearbook.presenter.PdfRendererView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.PDF_FILE_OBJECT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

/**
 * This fragment has a big [ImageView] that shows PDF pages, and 2 [Button]s to move between pages.
 */

class FragmentPdfRenderer : BaseFragment(), PdfRendererView {
    private var count = 1
    private lateinit var file: File
    override var presenter: Presenter = PdfRendererPresenter(this)
    private val _pdfFile = MutableSharedFlow<File>()
    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope
    override val pdfFile: SharedFlow<File>
        get() = _pdfFile

    private val _showPrevious = MutableSharedFlow<Boolean>()
    override val showPrevious: SharedFlow<Boolean>
        get() = _showPrevious

    private val _showNext = MutableSharedFlow<Boolean>()
    override val showNext: SharedFlow<Boolean>
        get() = _showNext

    override fun showPdfBitmap(pdfBitmap: SharedFlow<Bitmap>) {
        coroutineScope.launch {
            pdfBitmap.collect {
                Timber.d("BitmapInfo : ${it.byteCount}")
                binding.pdfImageView.setImageBitmap(it)
            }
        }
    }

    override fun showPageInfo(pageInfo: SharedFlow<Pair<Int, Int>>) {
        coroutineScope.launch {
            pageInfo.collect {
                binding.pageNumber.text = (it.first + 1).toString() + " / " + it.second
            }
        }
    }

    override fun enableNext(next: SharedFlow<Boolean>) {
        coroutineScope.launch {
            next.collect {
                binding.next.isEnabled = it
            }
        }
    }

    override fun enablePrevious(previous: SharedFlow<Boolean>) {
        coroutineScope.launch {
            previous.collect {
                binding.previous.isEnabled = it
            }
        }
    }

    private lateinit var binding: FragmentPdfRendererBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentPdfRendererBinding.inflate(layoutInflater, container, false)
        file = arguments?.getSerializable(PDF_FILE_OBJECT) as File
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coroutineScope.launch {
            _pdfFile.emit(file)
        }

        binding.apply {
            previous.setOnClickListener {
                coroutineScope.launch {
                    _showPrevious.emit(true)
                }
            }
            next.setOnClickListener {
                coroutineScope.launch {
                    _showNext.emit(true)
                }
            }
        }


    }
}
