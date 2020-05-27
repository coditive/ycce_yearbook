package com.syrous.ycceyearbook.ui.home.bottom_nav

import android.graphics.RectF
import android.graphics.drawable.Drawable

data class BottomNavItem(
    var title: String,
    val icon: Drawable,
    var rect: RectF = RectF(),
    var alpha: Int
)