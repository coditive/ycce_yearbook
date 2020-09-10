package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notifications")
data class Notification (
    @PrimaryKey val id: String,
    val readState: Int,
    val timestamp: Long?,
    val title: String?,
    val messageBody: String?
)