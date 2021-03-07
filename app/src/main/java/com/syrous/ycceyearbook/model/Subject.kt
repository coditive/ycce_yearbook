package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity(tableName = "subjects")
data class Subject (
    @PrimaryKey val course_code: String,
    val course: String,
    val department: String,
    val sem: Int
)