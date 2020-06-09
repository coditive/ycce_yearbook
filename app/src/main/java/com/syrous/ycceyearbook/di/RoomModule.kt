package com.syrous.ycceyearbook.di

import android.content.Context
import androidx.room.Room
import com.syrous.ycceyearbook.data.local.AllDao
import com.syrous.ycceyearbook.data.local.UserDao
import com.syrous.ycceyearbook.data.local.YearBookDatabase
import com.syrous.ycceyearbook.util.YEARBOOK_DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    private lateinit var yearBookDatabase: YearBookDatabase

    @Singleton
    @Provides
    fun provideYearBookDb(context: Context): YearBookDatabase{
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
    fun provideUserDao(db: YearBookDatabase): UserDao = db.userDao()

}
