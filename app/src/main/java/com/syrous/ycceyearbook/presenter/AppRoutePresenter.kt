package com.syrous.ycceyearbook.presenter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.action.DialogAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.ToastNotificationAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.RouteStore
import com.syrous.ycceyearbook.ui.home.bottom_nav.BottomNavView
import com.syrous.ycceyearbook.view.DialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterIsInstance
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class AppRoutePresenter constructor(
    private val activity: AppCompatActivity,
    dispatcher: Dispatcher,
    private val routeStore: RouteStore
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
    }

    override fun showDialogFragment(
        dialogFragment: DialogFragment,
        destination: RouteAction.DialogFragment
    ) {
        super.showDialogFragment(dialogFragment, destination)
    }

    override fun route(action: RouteAction) {
        activity.setTheme(R.style.AppTheme)
        when(action) {
            is RouteAction.StartUp -> navigateToFragment(R.id.fragment_splash)
            is RouteAction.Login -> navigateToFragment(R.id.fragment_login)
            is RouteAction.Welcome -> navigateToFragment(R.id.fragment_welcome)
            is DialogAction -> showDialog(action)
            is ToastNotificationAction -> showToastNotification(action)
        }
    }

    override fun findTransitionId(src: Int, dest: Int): Int? {
        return when(src to dest) {
            R.id.fragment_splash to R.id.fragment_login -> R.id.action_fragment_splash_to_fragment_login
            R.id.fragment_splash to R.id.fragment_welcome -> R.id.action_fragment_splash_to_fragment_welcome
            R.id.fragment_welcome to R.id.fragment_login -> R.id.action_fragment_welcome_to_fragment_login
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