package com.syrous.ycceyearbook.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.syrous.ycceyearbook.data.local.AllDao
import com.syrous.ycceyearbook.data.local.RecentDao
import com.syrous.ycceyearbook.data.local.YearBookDatabase
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
    fun provideAllDao(db: YearBookDatabase): AllDao = db.allDao()

    @Provides
    fun provideRecentDao(db: YearBookDatabase): RecentDao = db.recentDao()

    @Provides
    fun provideSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE)
    }

}
