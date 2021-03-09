package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.AccountAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.store.AccountStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginView {
    val loginButton: SharedFlow<Unit>
    val coroutineScope: CoroutineScope
    fun showAllView()
    fun hideAllView()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
}

class LoginPresenter(
    private val view: LoginView
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var accountStore: AccountStore

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            launch {
                view.loginButton.collect {
                    view.hideAllView()
                    dispatcher.dispatch(AccountAction.InitiateLogin)
                }
            }

            launch {
                accountStore.loading.collect {
                    loading ->
                    if(loading) {
                        view.hideAllView()
                        view.showLoadingIndicator()
                    } else {
                        view.hideLoadingIndicator()
                        view.showAllView()
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}