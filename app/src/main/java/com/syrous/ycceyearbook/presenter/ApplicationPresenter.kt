package com.syrous.ycceyearbook.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.syrous.ycceyearbook.ApplicationLifecycleDispatcher
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class ApplicationPresenter(
    private val appDispatcher: ApplicationLifecycleDispatcher
    ): DefaultLifecycleObserver, CoroutineScope {

    private lateinit var job: Job

    private val coroutineExceptionHandler = CoroutineExceptionHandler {
            _, throwable ->
        Timber.e(throwable)
    }

    override lateinit var coroutineContext: CoroutineContext

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        job = SupervisorJob()
        coroutineContext = Dispatchers.Unconfined + job + coroutineExceptionHandler
        appDispatcher.dispatchOnCreate()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        appDispatcher.dispatchOnStart()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        appDispatcher.dispatchOnResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        appDispatcher.dispatchOnPause()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        appDispatcher.dispatchOnStop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        job.cancel()
    }
}