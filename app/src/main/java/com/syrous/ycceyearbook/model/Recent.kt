package com.syrous.ycceyearbook.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "recents",
        foreignKeys = [
        ForeignKey(
            entity = Paper::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
        ]
)
data class Recent (
    @PrimaryKey val id: String,
    val type: String,
    val hits: Int
)