package com.syrous.ycceyearbook.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.syrous.ycceyearbook.data.local.DataDao
import com.syrous.ycceyearbook.data.local.NotificationDao
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.*
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
    fun provideRouteStore(dispatcher: Dispatcher, accountStore: AccountStore,
                          coroutineContext: CoroutineContext): RouteStore {
        return RouteStore(dispatcher, accountStore, coroutineContext)
    }

    @Singleton
    @Provides
    fun provideSentryStore(dispatcher: Dispatcher, coroutineContext: CoroutineContext): SentryStore {
        return SentryStore(dispatcher, coroutineContext)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Singleton
    @Provides
    fun provideNetworkStore(context: Application, dispatcher: Dispatcher,
                            coroutineContext: CoroutineContext): NetworkStore {
        return NetworkStore(context, dispatcher, coroutineContext)
    }

    @Singleton
    @Provides
    fun provideDataStore(
        dispatcher: Dispatcher, networkStore: NetworkStore, dataDao: DataDao,
        remoteApi: RemoteApi, coroutineContext: CoroutineContext): DataStore {
        return DataStore(dispatcher, networkStore, dataDao, remoteApi, coroutineContext)
    }

    @Singleton
    @Provides
    fun provideNotificationStore(
        dispatcher: Dispatcher, notificationDao: NotificationDao,
        coroutineContext: CoroutineContext): NotificationStore {
        return NotificationStore(dispatcher, notificationDao, coroutineContext)
    }

}