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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
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
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
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