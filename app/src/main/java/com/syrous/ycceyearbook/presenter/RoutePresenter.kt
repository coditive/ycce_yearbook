package com.syrous.ycceyearbook.presenter

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.view.Fragment as CustomFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.syrous.ycceyearbook.BuildConfig
import com.syrous.ycceyearbook.action.DialogAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.ToastNotificationAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.store.RouteStore
import com.syrous.ycceyearbook.view.DialogFragment
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
abstract class RoutePresenter(
    private val activity: AppCompatActivity,
    private val dispatcher: Dispatcher,
    private val routeStore: RouteStore
): Presenter(), CoroutineScope {

    protected lateinit var navController: NavController
    override lateinit var coroutineContext: CoroutineContext
    private lateinit var job: Job

    open val navHostFragmentManager: FragmentManager
     get()  {
         val fragmentManager = activity.supportFragmentManager
         val navHost = fragmentManager.fragments.last()
         return navHost.childFragmentManager
     }

    open val currentFragment: Fragment?
    get() {
        return navHostFragmentManager.fragments.lastOrNull()
    }

    class BackPressedCallback(
        private val enabled: Boolean = false,
        private val dispatcher: Dispatcher
    ): OnBackPressedCallback(enabled) {
        override fun handleOnBackPressed() {
            dispatcher.dispatch(RouteAction.InternalBack)
        }
    }

    override fun onBackPressed(): Boolean {
        dispatcher.dispatch(RouteAction.InternalBack)
        val fragment = currentFragment as? CustomFragment
        return fragment?.onBackPressed() ?: false
    }

    private val onBackPressedDispatcher = activity.onBackPressedDispatcher

    private val callback = BackPressedCallback(false, dispatcher)

    override fun onViewReady() {
        super.onViewReady()
        onBackPressedDispatcher.addCallback(activity, callback)
        job = Job()
        coroutineContext = Dispatchers.IO + job
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        onBackPressedDispatcher.addCallback(activity, callback)
    }

    protected abstract fun route(action: RouteAction)

    protected abstract fun findTransitionId(@IdRes src: Int, @IdRes dest: Int): Int?

    fun showDialog(destination: DialogAction) {
//        alertDialogStore.showDialog(activity, destination) TODO()
    }

    open fun showDialogFragment(
        dialogFragment: DialogFragment,
        destination: RouteAction.DialogFragment
    ) {
        try {
            dialogFragment.setTargetFragment(currentFragment, 0)
            dialogFragment.show(navHostFragmentManager, dialogFragment.javaClass.name)
            dialogFragment.setupDialog(destination.dialogTitle, destination.dialogSubtitle)
        } catch (e: IllegalStateException) {
//            log.error("Could not show dialog", e) TODO()
        }
    }

    fun showToastNotification(action: ToastNotificationAction) {
//        val toast = Toast(activity)
//        toast.duration = Toast.LENGTH_SHORT
//        val container = activity.window.decorView.rootView as ViewGroup
//
//        val layoutInflater = LayoutInflater.from(activity)
//
//        toast.view = layoutInflater.inflate(R.layout.toast_view, container, false)
//
//        val bottomMargin =
//            if (navController.currentDestination?.id == R.id.fragment_item_list) {
//                R.dimen.toast_bottom_margin_large
//            } else {
//                R.dimen.toast_bottom_margin_small
//            }
//
//        toast.setGravity(
//            Gravity.FILL_HORIZONTAL or Gravity.BOTTOM,
//            0,
//            activity.resources.getDimension(bottomMargin).toInt()
//        )
//
//        val view = toast.view.findViewById(R.id.message) as TextView
//        val message = action.viewModel.message
//        view.text = action.viewModel.messageParam?.let { activity.getString(message, it) }
//            ?: activity.getString(message)
//
//        val icon = toast.view.findViewById(R.id.icon) as ImageView
//        icon.setImageResource(action.viewModel.icon)
//
//        toast.show()
            TODO()
    }

    fun openWebsite(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent, null)
    }

    fun navigateToFragment(@IdRes destinationId: Int, args: Bundle? = null) {
        val src = navController.currentDestination ?: return
        val srcId = src.id
        if (srcId == destinationId) {
            val currentScreenArgs = navHostFragmentManager.fragments.lastOrNull()?.arguments
            if (args hasSameContentOf currentScreenArgs) {
                // No point in navigating if nothing has changed.
                return
            }
        }

        val transition = findTransitionId(srcId, destinationId) ?: destinationId

        val navOptions = if (transition == destinationId) {
            // Without being able to detect if we're in developer mode,
            // it is too dangerous to RuntimeException.
            if (BuildConfig.DEBUG) {
                val from = activity.resources.getResourceName(srcId)
                val to = activity.resources.getResourceName(destinationId)
                val graphName = activity.resources.getResourceName(navController.graph.id)
                throw IllegalStateException(
                    "Cannot route from $from to $to. " +
                            "This is a developer bug, fixable by adding an action to $graphName.xml and/or ${javaClass.simpleName}"
                )
            }
            null
        } else {
            // Get the transition action out of the graph, before we manually clear the back
            // stack, because it causes IllegalArgumentExceptions.
            src.getAction(transition)?.navOptions?.let { navOptions ->
                if (navOptions.shouldLaunchSingleTop()) {
                    while (navController.popBackStack()) {
                        // NOP
                    }
                    routeStore.clearBackStack()
                }
                navOptions
            }
        }

        try {
            navController.navigate(destinationId, args, navOptions)
        } catch (e: RuntimeException) {
            Timber.e(e.localizedMessage)
            navController.navigate(destinationId, args)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private infix fun Bundle?.hasSameContentOf(another: Bundle?): Boolean {
        if (this == null) {
            return another == null || another.isEmpty
        }

        if (size() != another?.size()) {
            return false
        }

        return keySet().all { key ->
            get(key) == another.get(key)
        }
    }


}
