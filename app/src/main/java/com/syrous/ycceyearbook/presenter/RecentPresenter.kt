package com.syrous.ycceyearbook.presenter

import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.store.RecentStore
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RecentPresenter(
    private val dispatcher: Dispatcher,
    private val recentStore: RecentStore
): Presenter() {

}