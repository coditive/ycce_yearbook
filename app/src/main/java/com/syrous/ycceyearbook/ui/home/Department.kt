package com.syrous.ycceyearbook.ui.home

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import java.io.Serializable

data class Department(
    val name: String,
    @DrawableRes val smallDrawableId: Int,
    @DrawableRes val largeDrawableId: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColor: Int,
    val position: Int
): Serializable