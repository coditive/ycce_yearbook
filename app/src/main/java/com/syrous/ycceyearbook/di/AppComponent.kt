package com.syrous.ycceyearbook.di

import android.app.Application
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.presenter.*
import com.syrous.ycceyearbook.ui.ActivityMain
import com.syrous.ycceyearbook.ui.notices.FragmentNotices
import com.syrous.ycceyearbook.ui.recent.FragmentRecent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Singleton
@Component(modules = [RoomModule::class, NetworkModule::class,
     FirebaseModule::class, SingletonModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Application,
                   @BindsInstance applicationCoroutineContext: CoroutineContext): AppComponent
    }

    fun inject(yearBookApplication: YearBookApplication)

    fun inject(activityMain: ActivityMain)

    fun inject(splashPresenter: SplashPresenter)

    fun inject(welcomePresenter: WelcomePresenter)

    fun inject(semPresenter: SemPresenter)

    fun inject(esePresenter: EsePresenter)

    fun inject(msePresenter: MsePresenter)

    fun inject(resourcePresenter: ResourcePresenter)

    fun inject(homePresenter: HomePresenter)

    fun inject(videoPlayerPresenter: VideoPlayerPresenter)

    fun inject(fragmentNotices: FragmentNotices)

    fun inject(fragmentRecent: FragmentRecent)

    fun inject(loginPresenter: LoginPresenter)

}