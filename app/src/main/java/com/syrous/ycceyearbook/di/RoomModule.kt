package com.syrous.ycceyearbook.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.syrous.ycceyearbook.data.local.*
import com.syrous.ycceyearbook.util.USER_SP_KEY
import com.syrous.ycceyearbook.util.YEARBOOK_DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    private lateinit var yearBookDatabase: YearBookDatabase

    @Singleton
    @Provides
    fun provideYearBookDb(context: Application): YearBookDatabase{
        yearBookDatabase = Room.databaseBuilder(
                            context,
                            YearBookDatabase::class.java,
                            YEARBOOK_DB_NAME
                            ).build()

        return yearBookDatabase
    }

    @Provides
    fun provideDataDao(db: YearBookDatabase): DataDao = db.dataDao()

    @Provides
    fun provideRecentDao(db: YearBookDatabase): RecentDao = db.recentDao()

    @Provides
    fun provideNotificationDao(db: YearBookDatabase): NotificationDao = db.notificationDao()

    @Provides
    fun provideDepartmentDao(db: YearBookDatabase): DepartmentDao = db.departmentDao()

    @Provides
    fun provideSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE)
    }

}
