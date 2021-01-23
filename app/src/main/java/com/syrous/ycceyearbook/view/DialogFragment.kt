package com.syrous.ycceyearbook.view

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.syrous.ycceyearbook.flux.Presenter
import androidx.fragment.app.DialogFragment as AndroidDialogFragment

open class DialogFragment: AndroidDialogFragment() {
    lateinit var presenter: Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewReady()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    open fun setupDialog(@StringRes titleId: Int, @StringRes subtitleId: Int? = null) {}
}