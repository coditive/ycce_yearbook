package com.syrous.ycceyearbook

import androidx.room.Room
import com.syrous.ycceyearbook.data.local.YearBookDatabase
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.util.BASE_URL
import com.syrous.ycceyearbook.util.YEARBOOK_DB_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer (application: YeaBookApplication) {

    val remoteApi: RemoteApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                this.addInterceptor(httpLoggingInterceptor)
            }
        }.build())
        .build()
        .create(RemoteApi::class.java)


    private val db = Room.databaseBuilder(application, YearBookDatabase::class.java,
                        YEARBOOK_DB_NAME).build()

    val allDao = db.allDao()
}