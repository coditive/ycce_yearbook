package com.syrous.ycceyearbook.presenter

import android.content.Context
import android.os.Bundle
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.action.RouteAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Department
import com.syrous.ycceyearbook.model.Semester
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.store.DataStore
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SemView {
    val coroutineScope: CoroutineScope
    val departmentName: SharedFlow<Department>
    val subjectClicks: SharedFlow<Subject>
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun addSemesterListToRecycler(semesterList: List<Semester>)
    fun showAllViews()
    fun hideAllViews()
}

class SemPresenter(
    private val view: SemView,
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var dataStore: DataStore

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            launch {
                view.departmentName.collect {
                    dispatcher.dispatch(DataAction.GetSubjects(it.name))
                }
            }

            launch {
                dataStore.loading.collect {
                   isLoading ->
                    if(isLoading) {
                        view.hideAllViews()
                        view.showLoadingIndicator()
                    }else {
                        view.hideLoadingIndicator()
                        view.showAllViews()
                    }
                }
            }

            launch {
                dataStore.semesterData.collect {
                    semesterList ->
                    view.addSemesterListToRecycler(semesterList)
                }
            }

            launch {
                view.subjectClicks.collect {
                        subject ->
                        val args = Bundle()
                        args.putSerializable(SUBJECT_OBJECT, subject)
                    dispatcher.dispatch(RouteAction.PaperAndResource(args))
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}