package com.syrous.ycceyearbook.ui.semester

import dagger.Subcomponent


@Subcomponent
interface SemComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(department: String): SemComponent
    }

    fun inject(fragmentSem: FragmentSem)
}