package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.store.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


interface MseView {
    val selectedSubject: SharedFlow<Subject>
    val msePaperClicks: SharedFlow<Paper>
    val coroutineScope: CoroutineScope
    fun hideAllViews()
    fun showAllViews()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun addPaperToRecycler(paperList: List<Paper>)
}


@ExperimentalCoroutinesApi
@FlowPreview
class MsePresenter(
    private val view: MseView
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var dataStore: DataStore

    override fun onViewReady() {
        super.onViewReady()

        view.coroutineScope.launch {
            launch {
                dataStore.msePaperData.collect { paperList ->
                    view.addPaperToRecycler(paperList)
                }
            }

            launch {
                view.selectedSubject.collect {
                    subject ->
                    dispatcher.dispatch(DataAction.GetMseData(subject.department, subject.sem,
                        subject.course_code))
                }
            }

            launch {
                dataStore.loading.collect {
                    isLoading ->
                    if(isLoading) {
                        view.hideAllViews()
                        view.showLoadingIndicator()
                    } else {
                        view.hideLoadingIndicator()
                        view.showAllViews()
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