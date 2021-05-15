package com.syrous.ycceyearbook.ui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.AccountAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.databinding.ActivityMainBinding
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.presenter.AppRoutePresenter
import com.syrous.ycceyearbook.presenter.AppRoutePresenterCallback
import com.syrous.ycceyearbook.store.*
import javax.inject.Inject

class ActivityMain : AppCompatActivity(), AppRoutePresenterCallback {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var routeStore: RouteStore

    @Inject
    lateinit var accountStore: AccountStore

    @Inject
    lateinit var downloadStore: DownloadStore

    @Inject
    lateinit var networkStore: NetworkStore

    @Inject
    lateinit var notificationStore: NotificationStore

    @Inject
    lateinit var recentStore: RecentStore

    @Inject
    lateinit var sentryStore: SentryStore

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    
    private val RC_SIGN_IN = 1001

    private lateinit var presenter: AppRoutePresenter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        (application as YearBookApplication).appComponent.inject(this)

        presenter = AppRoutePresenter(this, dispatcher, routeStore, accountStore)
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

    override fun initiateLogin() {
        val loginIntent = googleSignInClient.signInIntent
        startActivityForResult(loginIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                dispatcher.dispatch(AccountAction.FirebaseLogin(account))
            } catch (e: ApiException) {
                dispatcher.dispatch(SentryAction(e))
            }

        }
    }
}
