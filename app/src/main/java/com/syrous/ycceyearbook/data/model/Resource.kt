package com.syrous.ycceyearbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "resources")
data class Resource(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val contentType: String,
    val department: String,
    val sem: Int,
    val courseCode: String,
    val uploadedBy: String,
    val url: String,
    val timestamp: Long
)