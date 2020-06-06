package com.syrous.ycceyearbook.ui.login

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance account: GoogleSignInAccount, @BindsInstance auth: FirebaseAuth): LoginComponent
    }

    fun inject(fragmentLogin: FragmentLogin)
}