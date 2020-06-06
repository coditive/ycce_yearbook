package com.syrous.ycceyearbook.di

import com.syrous.ycceyearbook.BuildConfig
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.util.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().apply {
                            if (BuildConfig.DEBUG) {
                                val httpLoggingInterceptor = HttpLoggingInterceptor()
                                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                                addInterceptor(httpLoggingInterceptor)
                            }
                        }.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): RemoteApi = Retrofit.Builder()
                                                                .baseUrl(BASE_URL)
                                                                .addConverterFactory(MoshiConverterFactory.create())
                                                                .client(okHttpClient)
                                                                .build()
                                                                .create(RemoteApi::class.java)
}