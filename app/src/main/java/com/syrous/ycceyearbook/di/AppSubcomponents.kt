package com.syrous.ycceyearbook.di

import com.syrous.ycceyearbook.ui.login.LoginComponent
import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.util.user.UserComponent
import dagger.Module


@Module(subcomponents = [LoginComponent::class, UserComponent::class, PdfComponent::class])
interface AppSubcomponents