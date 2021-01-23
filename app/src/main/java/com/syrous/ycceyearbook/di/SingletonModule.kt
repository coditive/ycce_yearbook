package com.syrous.ycceyearbook.di

import androidx.appcompat.app.AppCompatActivity
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.RouteStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
@Module
class SingletonModule {

    @Singleton
    @Provides
    fun provideDispatcher(coroutineContext: CoroutineContext): Dispatcher {
        return Dispatcher(coroutineContext)
    }

    @Singleton
    @Provides
    fun provideRouteStore(dispatcher: Dispatcher, coroutineContext: CoroutineContext): RouteStore {
        return RouteStore(dispatcher, coroutineContext)
    }

}