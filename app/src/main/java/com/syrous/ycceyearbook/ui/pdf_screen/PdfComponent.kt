package com.syrous.ycceyearbook.ui.pdf_screen

import dagger.BindsInstance
import dagger.Subcomponent
import java.io.File


@Subcomponent
interface PdfComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance file: File?): PdfComponent
    }

    fun inject(fragmentPdfRenderer: FragmentPdfRenderer)

}