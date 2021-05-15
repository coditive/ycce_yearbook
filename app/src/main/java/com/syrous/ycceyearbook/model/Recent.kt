package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "recents",
        foreignKeys = [
        ForeignKey(
            entity = Paper::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
        ]
)
data class Recent (
    @PrimaryKey val id: String,
    val recentType: String,
    val contentType: String,
    val hits: Int
)