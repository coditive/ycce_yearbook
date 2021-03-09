package com.syrous.ycceyearbook.store

import android.app.Application
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import com.syrous.ycceyearbook.action.DownloadAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.util.PDF_FILE_OBJECT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class DownloadStore(
    private val dispatcher: Dispatcher,
    private val firebaseStorage: FirebaseStorage,
    private val context: Application,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .filterIsInstance<DownloadAction>()
                .collect { downloadAction ->
                    when(downloadAction) {
                        is DownloadAction.DownloadPaper -> {
                            downloadPaperFromUrl(downloadAction.paper)
                        }
                    }
                }
        }
    }




    private fun downloadPaperFromUrl(paper: Paper) = coroutineScope.launch{
        _loading.emit(true)
        try {
            val path = context.applicationContext.getExternalFilesDir("papers")
            Timber.d("Path : $path")
            val localFile = File(path, "paper${paper.id}.pdf")
            val reference = firebaseStorage.getReferenceFromUrl(paper.url)
            reference.getFile(localFile).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val args = Bundle()
                    Timber.d("File Successfully stored at location $path")
                    args.putSerializable(PDF_FILE_OBJECT, localFile)
                    dispatcher.dispatch(RouteAction.PdfRenderer(args))

                } else {
                    Timber.d("Downloading Exception: ${task.exception}")
                    task.exception?.let { SentryAction(it) }?.let { dispatcher.dispatch(it) }
                }
                coroutineScope.launch {
                    _loading.emit(false)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            dispatcher.dispatch(SentryAction(e))
        }
    }
}