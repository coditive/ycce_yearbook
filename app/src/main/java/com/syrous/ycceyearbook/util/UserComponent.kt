package com.syrous.ycceyearbook.util

import dagger.Subcomponent


@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }


}