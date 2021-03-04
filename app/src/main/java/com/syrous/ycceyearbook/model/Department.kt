package com.syrous.ycceyearbook.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "departments")
data class Department(
    @PrimaryKey val id: Int,
    val name: String,
    @DrawableRes val smallDrawableId: Int,
    @DrawableRes val largeDrawableId: Int,
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColor: Int,
    val position: Int
): Serializable