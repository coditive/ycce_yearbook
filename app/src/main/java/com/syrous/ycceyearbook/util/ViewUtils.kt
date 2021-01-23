package com.syrous.ycceyearbook.util

import android.view.View


fun showView(vararg views: View) {
    for(view in views)
        view.visibility = View.VISIBLE
}

fun removeView(view: View) {
    view.visibility = View.GONE
}

fun <S : View, R : View?> showAndRemove(toShow: S, vararg toRemove: R) {
    showView(toShow)
    for (view in toRemove) {
        if (view != null) {
            removeView(view)
        }
    }
}