package com.syrous.ycceyearbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Recent
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject

@Database(
    entities = [Subject::class, Paper::class, Resource::class, Recent::class],
    version = 1,
    exportSchema = true)
abstract class YearBookDatabase: RoomDatabase(){
    abstract fun allDao(): AllDao
    abstract fun recentDao(): RecentDao
}