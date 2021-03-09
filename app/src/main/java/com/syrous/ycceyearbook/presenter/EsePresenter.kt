package com.syrous.ycceyearbook.presenter

import android.content.Context
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.action.DownloadAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.store.DataStore
import com.syrous.ycceyearbook.store.DownloadStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


interface EseView {
    val selectedSubject: SharedFlow<Subject>
    val coroutineScope: CoroutineScope
    val esePaperClicks: SharedFlow<Paper>
    fun hideAllViews()
    fun showAllViews()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
    fun addPaperToRecycler(paperList: List<Paper>)
}

@FlowPreview
@ExperimentalCoroutinesApi
class EsePresenter(
    private val view: EseView
): Presenter() {

    @Inject
    lateinit var dispatcher: Dispatcher

    @Inject
    lateinit var dataStore: DataStore

    @Inject
    lateinit var downloadStore: DownloadStore

    override fun onViewReady() {
        super.onViewReady()
        view.coroutineScope.launch {
            launch {
                view.esePaperClicks.collect {
                        paper ->
                    dispatcher.dispatch(DownloadAction.DownloadPaper(paper))
                }
            }

            launch {
                view.selectedSubject.collect {
                    subject ->
                    dispatcher.dispatch(DataAction.GetEseData(subject.department, subject.sem,
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

            launch {
                downloadStore.loading.collect {
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

            launch {
                dataStore.esePaperData.collect {
                    paperList ->
                    view.addPaperToRecycler(paperList)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as YearBookApplication).appComponent.inject(this)
    }
}