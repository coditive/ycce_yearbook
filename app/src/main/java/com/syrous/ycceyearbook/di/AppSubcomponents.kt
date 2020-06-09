package com.syrous.ycceyearbook.di

import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.util.UserComponent
import dagger.Module


@Module(subcomponents = [LoginComponent::class, UserComponent::class])
interface AppSubcomponents