package com.syrous.ycceyearbook.data.remote

import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Subject
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("/api/get_subjects?department={department}&sem={sem}")
    fun getSubjects(@Path("department") department: String, @Path("sem") sem: Int): List<Subject>

    @GET("/api/get_mse_papers?department={department}&sem={sem}&course_code={course_code}")
    fun getMsePapers(@Path("department") department: String, @Path("sem") sem: Int,
                     @Path("course_code") courseCode: String): List<Paper>

    @GET("/api/get_ese_papers?department={department}&sem={sem}&course_code={course_code}")
    fun getEsePapers(@Path("department") department: String, @Path("sem") sem: Int,
                     @Path("course_code") courseCode: String): List<Paper>

    @GET("/api/get_resources?department={department}&sem={sem}&course_code={course_code}")
    fun getResource(@Path("department") department: String, @Path("sem") sem: Int,
                    @Path("course_code") courseCode: String): List<Resource>
}