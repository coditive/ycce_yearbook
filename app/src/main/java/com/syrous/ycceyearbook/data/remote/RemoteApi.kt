package com.syrous.ycceyearbook.data.remote

import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("get_subjects")
    suspend fun getSubjects(@Query("department") department: String, @Query("sem") sem: Int): List<Subject>

    @GET("get_mse_papers")
    suspend fun getMsePapers(@Query("department") department: String, @Query("sem") sem: Int,
                     @Query("course_code") courseCode: String): List<Paper>

    @GET("get_ese_papers")
    suspend fun getEsePapers(@Query("department") department: String, @Query("sem") sem: Int,
                     @Query("course_code") courseCode: String): List<Paper>

    @GET("get_resources")
    suspend fun getResource(@Query("department") department: String, @Query("sem") sem: Int,
                    @Query("course_code") courseCode: String): List<Resource>
}