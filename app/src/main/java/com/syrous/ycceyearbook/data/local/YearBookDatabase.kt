package com.syrous.ycceyearbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Subject

@Database(
    entities = [Subject::class, Paper::class, Resource::class],
    version = 1,
    exportSchema = true)
abstract class YearBookDatabase: RoomDatabase(){
    abstract fun allDao(): AllDao
}