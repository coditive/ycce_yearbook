package com.syrous.ycceyearbook.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.store.AccountStore
import com.syrous.ycceyearbook.store.NetworkStore
import com.syrous.ycceyearbook.store.RouteStore
import com.syrous.ycceyearbook.store.SentryStore
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
    fun provideNetworkStore(context: Context, dispatcher: Dispatcher,
                            coroutineContext: CoroutineContext): NetworkStore {
        return NetworkStore(context, dispatcher, coroutineContext)
    }
}