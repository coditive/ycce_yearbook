package com.syrous.ycceyearbook

import android.app.Application
import timber.log.Timber

class YeaBookApplication : Application(){


    override fun onCreate() {
        super.onCreate()

        Timber.plant()
    }
}
