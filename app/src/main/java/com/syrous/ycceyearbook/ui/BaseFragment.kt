package com.syrous.ycceyearbook.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.flux.Presenter

abstract class BaseFragment: Fragment() {
    abstract var presenter: Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewReady()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.onAttach(context)
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

    fun onBackPressed(): Boolean = presenter.onBackPressed()
}