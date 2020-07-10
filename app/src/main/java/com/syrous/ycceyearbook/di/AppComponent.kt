package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.data.local.UserProfile
import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.semester.FragmentSem
import com.syrous.ycceyearbook.ui.splash.FragmentSplash
import com.syrous.ycceyearbook.util.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RoomModule::class, NetworkModule::class, AppSubcomponents::class, FirebaseModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun userManager(): UserManager

    fun userProfile(): UserProfile

    fun loginComponent(): LoginComponent.Factory

    fun pdfComponent(): PdfComponent.Factory

    fun inject(fragmentSplash: FragmentSplash)

    fun inject(fragmentSem: FragmentSem)

}