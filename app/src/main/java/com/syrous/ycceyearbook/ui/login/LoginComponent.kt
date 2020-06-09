package com.syrous.ycceyearbook.ui.login

import com.syrous.ycceyearbook.util.UserManager
import dagger.Subcomponent


@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun userManager(): UserManager

    fun inject(fragmentLogin: FragmentLogin)
}