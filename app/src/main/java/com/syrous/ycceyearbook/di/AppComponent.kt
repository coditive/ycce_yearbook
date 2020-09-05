package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.ui.home.FragmentHome
import com.syrous.ycceyearbook.ui.login.FragmentLogin
import com.syrous.ycceyearbook.ui.notices.FragmentNotices
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentEse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentMse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentPaperAndResource
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentResource
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.recent.FragmentRecent
import com.syrous.ycceyearbook.ui.semester.SemComponent
import com.syrous.ycceyearbook.util.user.UserDataRepository
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

    fun userRepository(): UserDataRepository

    fun pdfComponent(): PdfComponent.Factory

    fun semComponent(): SemComponent.Factory

    fun inject(fragmentPaperAndResource: FragmentPaperAndResource)

    fun inject(fragmentEse: FragmentEse)

    fun inject(fragmentMse: FragmentMse)

    fun inject(fragmentResource: FragmentResource)

    fun inject(fragmentHome: FragmentHome)

    fun inject(fragmentNotices: FragmentNotices)

    fun inject(fragmentRecent: FragmentRecent)

    fun inject(fragmentLogin: FragmentLogin)

}