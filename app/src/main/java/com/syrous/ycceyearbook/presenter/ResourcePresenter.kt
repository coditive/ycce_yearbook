package com.syrous.ycceyearbook.presenter

import android.content.Context
import android.os.Bundle
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.store.DataStore
import com.syrous.ycceyearbook.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


interface ResourceView {
    val selectedSubject: SharedFlow<Subject>
    val coroutineScope: CoroutineScope
    val resourceClicks: SharedFlow<Resource>
    fun hideAllViews()
    fun showAllViews()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun addResourceToRecycler(resourceList: List<Resource>)
}

class ResourcePresenter(
    private val view: ResourceView
): Presenter() {
    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var dataStore: DataStore

    override fun onViewReady() {
        super.onViewReady()

        view.coroutineScope.launch {
            launch {
                view.resourceClicks.collect { resource ->
                    val args = Bundle()
                    args.putSerializable(Constant.Resource, resource)
                    dispatcher.dispatch(RouteAction.VideoPlayer(args))
                }
            }

            launch {
                view.selectedSubject.collect { subject ->
                    dispatcher.dispatch(
                        DataAction.GetResource(
                            subject.department, subject.sem,
                            subject.course_code
                        )
                    )
                }
            }

            launch {
                dataStore.loading.collect { isLoading ->
                    if (isLoading) {
                        view.hideAllViews()
                        view.showLoadingIndicator()
                    } else {
                        view.hideLoadingIndicator()
                        view.showAllViews()
                    }
                }
            }

            launch {
                dataStore.resourceData.collect {
                    resourceList ->
                    view.addResourceToRecycler(resourceList)
                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}