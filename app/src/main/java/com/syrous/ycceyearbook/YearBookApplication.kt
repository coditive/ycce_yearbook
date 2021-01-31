package com.syrous.ycceyearbook

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.action.LifecycleAction
import com.syrous.ycceyearbook.di.AppComponent
import com.syrous.ycceyearbook.di.DaggerAppComponent
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.presenter.ApplicationPresenter
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface ApplicationLifecycleDispatcher {
    fun dispatchOnCreate()
    fun dispatchOnStart()
    fun dispatchOnPause()
    fun dispatchOnResume()
    fun dispatchOnStop()
}

@FlowPreview
@ExperimentalCoroutinesApi
class YearBookApplication : Application(), ApplicationLifecycleDispatcher{

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext as Application,
            presenter.coroutineContext)
    }

    @Inject
    lateinit var dispatcher: Dispatcher

    private lateinit var presenter: ApplicationPresenter

    override fun onCreate() {
        super.onCreate()
        setupLifecycleListener()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupLifecycleListener() {
        // Watch for application lifecycle and take appropriate actions
        presenter = ApplicationPresenter(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(presenter)
    }

    override fun dispatchOnCreate() {
        appComponent.inject(this)
        dispatcher.dispatch(LifecycleAction.Startup)
    }

    override fun dispatchOnStart() {
        //NO-OP
    }

    override fun dispatchOnPause() {
        dispatcher.dispatch(LifecycleAction.Background)
    }

    override fun dispatchOnResume() {
        dispatcher.dispatch(LifecycleAction.Foreground)
    }

    override fun dispatchOnStop() {
        dispatcher.dispatch(LifecycleAction.ShutDown)
    }
}
