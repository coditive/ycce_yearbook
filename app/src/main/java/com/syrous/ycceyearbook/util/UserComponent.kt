package com.syrous.ycceyearbook.util

import com.syrous.ycceyearbook.ui.home.FragmentHome
import com.syrous.ycceyearbook.ui.notices.FragmentNotices
import com.syrous.ycceyearbook.ui.recent.FragmentRecent
import dagger.Subcomponent


@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    fun inject(fragmentHome: FragmentHome)

    fun inject(fragmentNotices: FragmentNotices)

    fun inject(fragmentRecent: FragmentRecent)

}