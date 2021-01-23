package com.syrous.ycceyearbook.ui

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.ActivityMainBinding
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.presenter.AppRoutePresenter
import com.syrous.ycceyearbook.store.RouteStore
import io.sentry.Sentry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ActivityMain : AppCompatActivity() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var routeStore: RouteStore

    private lateinit var presenter: AppRoutePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        (application as YearBookApplication).appComponent.inject(this)
        presenter = AppRoutePresenter(this, dispatcher, routeStore)

        setContentView(binding.root)
        presenter.onViewReady()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onBackPressed() {
       if(!presenter.onBackPressed()) {
           super.onBackPressed()
       }
    }
}
