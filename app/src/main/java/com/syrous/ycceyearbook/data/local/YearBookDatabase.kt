package com.syrous.ycceyearbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syrous.ycceyearbook.model.*

@Database(
    entities = [
        Subject::class, Paper::class, Resource::class, Recent::class, Notification::class,
        Department::class],
    version = 1,
    exportSchema = true)
abstract class YearBookDatabase: RoomDatabase(){
    abstract fun dataDao(): DataDao
    abstract fun recentDao(): RecentDao
    abstract fun notificationDao(): NotificationDao
    abstract fun departmentDao(): DepartmentDao
}