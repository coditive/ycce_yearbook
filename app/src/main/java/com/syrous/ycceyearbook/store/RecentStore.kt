package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.RecentAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.RecentDao
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Recent
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.util.toRecent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
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
            dispatcher.getDispatcherChannelSubscription()
                .filterIsInstance<RecentAction>()
                .collect {
                    recentAction ->
                    when(recentAction) {
                        RecentAction.GetRecentPapers -> observeRecentPapers()
                        is RecentAction.StoreRecentPaper -> storeRecentPaper(recentAction.paper)
                        is RecentAction.StoreRecentResource -> storeRecentResource(recentAction.resource)
                    }
                }
        }
    }

    private fun observeRecentPapers() = coroutineScope.launch {
        recentDao.observeRecentPapers()
            .collect {
                _recentData.emit(it)
            }
    }

    private fun storeRecentPaper(paper: Paper) = coroutineScope.launch {
        try {
            recentDao.insertRecent(paper.toRecent())
        } catch (e: Exception) {
            Timber.e(e)
            dispatcher.dispatch(SentryAction(e))
        }
    }

    private fun storeRecentResource(resource: Resource) = coroutineScope.launch {
        try {
            recentDao.insertRecent(resource.toRecent())
        } catch (e: Exception) {
            Timber.e(e)
            dispatcher.dispatch(SentryAction(e))
        }
    }
}