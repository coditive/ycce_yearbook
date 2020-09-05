package com.syrous.ycceyearbook.di

import com.syrous.ycceyearbook.ui.pdf_screen.PdfComponent
import com.syrous.ycceyearbook.ui.semester.SemComponent
import dagger.Module


@Module(subcomponents = [PdfComponent::class, SemComponent::class])
interface AppSubcomponents