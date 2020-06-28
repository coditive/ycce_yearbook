package com.syrous.ycceyearbook.di

import android.content.Context
import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.ui.semester.FragmentSem
import com.syrous.ycceyearbook.util.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, NetworkModule::class, AppSubcomponents::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun userManager() : UserManager

    fun loginComponent(): LoginComponent.Factory

    fun inject(fragmentSem: FragmentSem)
}