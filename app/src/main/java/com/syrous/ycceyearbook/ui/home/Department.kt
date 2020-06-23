package com.syrous.ycceyearbook.ui.home

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Department(
    val name: String,
    @DrawableRes val drawableId: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColor: Int,
    val position: Int
)