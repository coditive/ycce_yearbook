package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "papers")
data class Paper(
    @PrimaryKey val id: String,
    val department: String,
    val sem: Int,
    val term: String,
    val courseCode: String,
    val exam: String,
    val uploaded_by: String,
    val year: String,
    val url: String,
    val timestamp: Long
)