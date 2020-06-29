package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.semester.FragmentSem
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
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory

    fun pdfComponent(): PdfComponent.Factory

    fun inject(fragmentSem: FragmentSem)

}