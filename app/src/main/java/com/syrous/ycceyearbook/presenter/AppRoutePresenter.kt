package com.syrous.ycceyearbook.presenter

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.action.*
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.AccountStore
import com.syrous.ycceyearbook.store.RouteStore
import com.syrous.ycceyearbook.ui.ActivityMain
import com.syrous.ycceyearbook.ui.home.bottom_nav.BottomNavView
import com.syrous.ycceyearbook.view.DialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber


interface AppRoutePresenterCallback {
    fun initiateLogin()
}

@FlowPreview
@ExperimentalCoroutinesApi
class AppRoutePresenter constructor(
    private val activity: AppCompatActivity,
    private val dispatcher: Dispatcher,
    private val routeStore: RouteStore,
    private val accountStore: AccountStore
    ): RoutePresenter(activity, dispatcher, routeStore) {

    override fun onViewReady() {
        super.onViewReady()
        navController = Navigation.findNavController(activity, R.id.nav_host_fragment)
        Timber.d("Navigation Controller Found and Navigating")
        navController.addOnDestinationChangedListener {
                _, destination, _ ->
            toggleBottomNavVisibility(destination.id)
        }
    }

    private fun toggleBottomNavVisibility(destinationId: Int) {
        when(destinationId) {
            R.id.action_fragment_splash_to_fragment_home ->
                activity.findViewById<BottomNavView>(R.id.bottomBar).visibility = View.VISIBLE

            else -> activity.findViewById<BottomNavView>(R.id.bottomBar).visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        routeStore.routes.asLiveData().observe(activity) {
            routeAction -> route(routeAction)
        }

        accountStore.accountState.asLiveData().observe(activity) {
          accountState ->
            if(accountState is AccountStore.State.GoogleLogin) {
                Timber.d("AccountStore State GoogleLogin Got and Login initiated on in Activity")
                    (activity as ActivityMain).initiateLogin()
            }
        }
    }

    override fun route(action: RouteAction) {
        activity.setTheme(R.style.AppTheme)
        when(action) {
            is RouteAction.StartUp -> {
                navigateToFragment(R.id.fragment_splash)
                dispatcher.dispatch(AccountAction.AutomaticLogin)
            }
            is RouteAction.Login -> navigateToFragment(R.id.fragment_login)
            is RouteAction.Welcome -> navigateToFragment(R.id.fragment_welcome)
            is RouteAction.Home -> navigateToFragment(R.id.fragment_home)
            is DialogAction -> showDialog(action)
            is ToastNotificationAction -> showToastNotification(action)
        }
    }

    override fun findTransitionId(src: Int, dest: Int): Int? {
        return when(src to dest) {
            R.id.fragment_splash to R.id.fragment_login -> R.id.action_fragment_splash_to_fragment_login
            R.id.fragment_splash to R.id.fragment_welcome -> R.id.action_fragment_splash_to_fragment_welcome
            R.id.fragment_welcome to R.id.fragment_login -> R.id.action_fragment_welcome_to_fragment_login
            R.id.fragment_splash to R.id.fragment_home -> R.id.action_fragment_splash_to_fragment_home
            R.id.fragment_login to R.id.fragment_home -> R.id.action_fragment_login_to_fragmentHome
            R.id.fragment_home to R.id.fragment_notices -> R.id.action_fragmentHome_to_fragmentNotices
            R.id.fragment_home to R.id.fragment_recent -> R.id.action_fragmentHome_to_fragmentRecent
            R.id.fragment_home to R.id.fragment_more -> R.id.action_fragmentHome_to_fragmentMore
            R.id.fragment_home to R.id.fragment_sem -> R.id.action_fragmentHome_to_fragmentSem
            R.id.fragment_sem to R.id.fragment_paper_and_resource -> R.id.action_fragmentSem_to_fragmentPaperAndResource
            R.id.fragment_paper_and_resource to R.id.fragment_pdf_renderer -> R.id.action_fragmentPaperAndResource_to_fragmentPdfRenderer
            else -> null
        }
    }
}