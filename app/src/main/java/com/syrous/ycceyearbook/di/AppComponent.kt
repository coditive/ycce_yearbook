package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.data.local.UserProfile
import com.syrous.ycceyearbook.ui.home.FragmentHome
import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentEse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentMse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentResource
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.semester.FragmentSem
import com.syrous.ycceyearbook.ui.splash.FragmentSplash
import com.syrous.ycceyearbook.util.user.UserComponent
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

    fun userComponent(): UserComponent.Factory

    fun loginComponent(): LoginComponent.Factory

    fun pdfComponent(): PdfComponent.Factory

    fun inject(fragmentSplash: FragmentSplash)

    fun inject(fragmentSem: FragmentSem)

    fun inject(fragmentEse: FragmentEse)

    fun inject(fragmentMse: FragmentMse)

    fun inject(fragmentResource: FragmentResource)

}