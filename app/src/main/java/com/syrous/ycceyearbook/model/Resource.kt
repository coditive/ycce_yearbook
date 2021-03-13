package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity(tableName = "resources")
data class Resource(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    @Json(name = "content_type") val contentType: String,
    val department: String,
    val sem: Int,
    @Json(name = "course_code")val courseCode: String,
    @Json(name = "uploaded_by")val uploadedBy: String,
    val url: String,
    val timestamp: Long
): Serializable