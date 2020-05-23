package com.syrous.ycceyearbook.data.remote

import com.syrous.ycceyearbook.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.net.ssl.HttpsURLConnection

class MockWebServerWrapper (private val mockWebServer: MockWebServer) {

    fun startServer() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpsURLConnection.HTTP_OK)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("[{\n\t\"course_code\": \"GE1001\",\n\t\"course\": \"Mathematics\",\n\t\"department\": \"ct\",\n\t\"sem\": 3\n}]"))
        mockWebServer.start()
    }

    fun stopServer() {
        mockWebServer.shutdown()
    }


    fun setupRetrofit(BASE_URL: String): RemoteApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(httpLoggingInterceptor)
                }
            }.build())
            .build()
            .create(RemoteApi::class.java)
    }




    fun getBaseUrl(url: String): HttpUrl {
        println(mockWebServer.url(url))
        return mockWebServer.url(url)
    }


    // "/get_server" -> MockResponse().setResponseCode(HttpsURLConnection.HTTP_OK).addHeader("Content-Type", "application/json; charset=utf-8").setBody("{}")


    fun getServer(): MockWebServer {
        return mockWebServer
    }

}