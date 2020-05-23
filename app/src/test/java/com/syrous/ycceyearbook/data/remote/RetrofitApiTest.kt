package com.syrous.ycceyearbook.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RetrofitApiTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServerWrapper: MockWebServerWrapper

    private lateinit var remoteApi: RemoteApi

    @Before
    fun initializeMockWebServer() {
        mockWebServerWrapper = MockWebServerWrapper(MockWebServer())
        mockWebServerWrapper.startServer()
        remoteApi = mockWebServerWrapper.setupRetrofit(mockWebServerWrapper.getBaseUrl("/").toString())
    }



    @Test
    fun getSubjectsApi_paramForDepartmentAndSem_200ResponseFromMock() {
        //Give ->

        //when ->
        val response = remoteApi.getSubjects("ct", 3)

        //then ->
        assertThat(response).isEmpty()
    }






    @After
    fun releaseAllUsedResources () {
        mockWebServerWrapper.stopServer()
    }




}