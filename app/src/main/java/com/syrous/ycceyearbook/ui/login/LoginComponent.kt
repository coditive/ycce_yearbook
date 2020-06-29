package com.syrous.ycceyearbook.ui.login

import dagger.Subcomponent


@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(fragmentLogin: FragmentLogin)
}