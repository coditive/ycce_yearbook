package com.syrous.ycceyearbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syrous.ycceyearbook.model.*

@Database(
    entities = [Subject::class, Paper::class, Resource::class, User::class, Recent::class],
    version = 1,
    exportSchema = true)
abstract class YearBookDatabase: RoomDatabase(){
    abstract fun allDao(): AllDao
    abstract fun recentDao(): RecentDao
}