package com.syrous.ycceyearbook.presenter

import android.content.Context
import android.os.Bundle
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.NetworkAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Department
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


interface HomeView {
    val coroutineScope: CoroutineScope
    val departmentClicks: SharedFlow<Department>
    fun displayUserName(user: User)
    fun displayProfilePic(user: User)
}

class HomePresenter(
    private val view: HomeView
    ): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var userStorage: UserSharedPrefStorage

    override fun onViewReady() {
        super.onViewReady()

        try {
            val result = userStorage.getLoggedInUser()
            if(result is Success) {
                view.displayUserName(result.data)
                view.displayProfilePic(result.data)
            } else if(result is Error){
                Timber.e(result.exception)
            }
        } catch (e: Exception) {
            Timber.d(e)
            dispatcher.dispatch(SentryAction(e))
        }

        view.coroutineScope.launch {
            view.departmentClicks.collect { department ->
                val args = Bundle()
                args.putSerializable(Constant.Department.DEPARTMENT_NAME, department)
                dispatcher.dispatch(RouteAction.Semester(args))
            }
        }

        dispatcher.dispatch(NetworkAction.CheckConnectivity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}