package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.ui.ActivityMain
import com.syrous.ycceyearbook.ui.home.FragmentHome
import com.syrous.ycceyearbook.ui.login.FragmentGreeting
import com.syrous.ycceyearbook.ui.login.FragmentLogin
import com.syrous.ycceyearbook.ui.notices.FragmentNotices
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentEse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentMse
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentPaperAndResource
import com.syrous.ycceyearbook.ui.papers_and_resources.FragmentResource
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.recent.FragmentRecent
import com.syrous.ycceyearbook.ui.semester.SemComponent
import com.syrous.ycceyearbook.ui.splash.FragmentSplash
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [RoomModule::class, NetworkModule::class,
    AppSubcomponents::class, FirebaseModule::class, SingletonModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Application,
                   @BindsInstance applicationCoroutineContext: CoroutineContext): AppComponent
    }

    fun pdfComponent(): PdfComponent.Factory

    fun semComponent(): SemComponent.Factory

    fun inject(yearBookApplication: YearBookApplication)

    fun inject(activityMain: ActivityMain)

    fun inject(fragmentSplash: FragmentSplash)

    fun inject(fragmentGreeting: FragmentGreeting)

    fun inject(fragmentPaperAndResource: FragmentPaperAndResource)

    fun inject(fragmentEse: FragmentEse)

    fun inject(fragmentMse: FragmentMse)

    fun inject(fragmentResource: FragmentResource)

    fun inject(fragmentHome: FragmentHome)

    fun inject(fragmentNotices: FragmentNotices)

    fun inject(fragmentRecent: FragmentRecent)

    fun inject(fragmentLogin: FragmentLogin)

}