package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.RecentAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.RecentDao
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Recent
import com.syrous.ycceyearbook.util.toRecent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class RecentStore(
    private val dispatcher: Dispatcher,
    private val recentDao: RecentDao,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    private val _recentData = MutableStateFlow(emptyList<Recent>())
    val recentData: StateFlow<List<Recent>> = _recentData

    init {
        coroutineScope.launch {
            Timber.d("recentStore Coroutine Context State : ${this.isActive}")
            dispatcher.getDispatcherChannelSubscription()
                .filterIsInstance<RecentAction>()
                .collect {
                    recentAction ->
                    when(recentAction) {
                        RecentAction.GetRecentPapers -> observeRecentPapers()
                        is RecentAction.StoreRecentPaper -> storeRecentPaper(recentAction.paper)
                    }
                }
        }
    }

    private suspend fun observeRecentPapers() =
        recentDao.observeRecentPapers()
            .collect {
                _recentData.emit(it)
            }

    private suspend fun storeRecentPaper(paper: Paper) {
        try {
            recentDao.insertRecentPaper(paper.toRecent())
        } catch (e: Exception) {
            dispatcher.dispatch(SentryAction(e))
        }
    }
}