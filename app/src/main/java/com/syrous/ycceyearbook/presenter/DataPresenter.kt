package com.syrous.ycceyearbook.presenter

import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.store.DataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DataPresenter(
    private val dispatcher: Dispatcher,
    private val dataStore: DataStore
): Presenter() {

}