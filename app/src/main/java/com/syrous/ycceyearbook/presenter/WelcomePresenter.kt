package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


interface WelcomeView {
    val okButtonClicks: SharedFlow<Unit>
    val coroutineScope: CoroutineScope
    fun hideViewObjects()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
}

@FlowPreview
@ExperimentalCoroutinesApi
class WelcomePresenter(
    private val view: WelcomeView
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            view.okButtonClicks.collect {
                dispatcher.dispatch(RouteAction.Login)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}