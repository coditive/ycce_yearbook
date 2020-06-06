package com.syrous.ycceyearbook.di

import android.content.Context
import com.syrous.ycceyearbook.ui.login.LoginComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [RoomModule::class, NetworkModule::class, AppSubcomponents::class])
@Singleton
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory

}