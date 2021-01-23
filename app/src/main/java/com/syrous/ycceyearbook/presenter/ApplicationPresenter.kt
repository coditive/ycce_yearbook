package com.syrous.ycceyearbook.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.syrous.ycceyearbook.ApplicationLifecycleDispatcher
import com.syrous.ycceyearbook.action.LifecycleAction
import com.syrous.ycceyearbook.flux.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class ApplicationPresenter(
    private val appDispatcher: ApplicationLifecycleDispatcher
    ): DefaultLifecycleObserver, CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        job = Job()
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