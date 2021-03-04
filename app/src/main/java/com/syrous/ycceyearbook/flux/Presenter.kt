package com.syrous.ycceyearbook.flux

import android.content.Context
import androidx.annotation.CallSuper

abstract class Presenter {

    open fun onViewReady() {

    }

    open fun onAttach(context: Context) {

    }

    @CallSuper
    open fun onDestroy() {
        // NOOP
    }

    @CallSuper
    open fun onResume() {
        // NOOP
    }

    @CallSuper
    open fun onPause() {
        // NOOP
    }

    @CallSuper
    fun onStop() {
        // NOOP
    }

    /**
     * Called by the fragment when the back button is pressed.
     *
     * @return `true` if the back button event has been handled by the presenter.
     * By default, returns false, in which case Android handles the event.
     */
    open fun onBackPressed(): Boolean = false
}