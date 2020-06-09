package com.syrous.ycceyearbook

import android.app.Application
import com.syrous.ycceyearbook.di.AppComponent
import com.syrous.ycceyearbook.di.DaggerAppComponent
import timber.log.Timber

class YearBookApplication : Application(){

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
