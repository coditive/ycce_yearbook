package com.syrous.ycceyearbook.ui.semester

import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent
interface SemComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance department: String): SemComponent
    }

    fun inject(fragmentSem: FragmentSem)
}