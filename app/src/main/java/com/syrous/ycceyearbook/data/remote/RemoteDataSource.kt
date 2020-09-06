package com.syrous.ycceyearbook.data.remote

import androidx.lifecycle.LiveData
import com.syrous.ycceyearbook.data.LibraryDataSource
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.Subject
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remoteApi: RemoteApi
): LibraryDataSource {

    override fun observeSubjects(department: String, sem: Int): LiveData<Result<List<Subject>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjects(department: String, sem: Int): Result<List<Subject>> {
        return try {
                val subjects = remoteApi.getSubjects(department, sem)
                Success(subjects)
            } catch (e: Exception){
                Error(e)
        }
    }

    override suspend fun refreshSubjects(department: String, sem: Int) {
        TODO("Not yet implemented")
    }

    override fun observePapers(department: String, sem: Int, courseCode: String, exam: String): LiveData<Result<List<Paper>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPapers(department: String, sem: Int, courseCode: String, exam: String): Result<List<Paper>> {
        return try {
                if(exam == "mse"){
                    val papers = remoteApi.getMsePapers(department, sem, courseCode)
                    Success(papers)
                } else {
                    val papers = remoteApi.getEsePapers(department, sem, courseCode)
                    Success(papers)
                }
        } catch (e: Exception){
                Error(e)
        }
    }

    override suspend fun refreshPapers(
        department: String,
        sem: Int,
        courseCode: String,
        exam: String
    ) {
        TODO("Not yet implemented")
    }

    override fun observeResources(department: String, sem: Int, courseCode: String): LiveData<Result<List<Resource>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResources(department: String, sem: Int, courseCode: String): Result<List<Resource>> {
        return try {
            val resources = remoteApi.getResource(department, sem, courseCode)
            Success(resources)
        } catch (e: Exception){
            Error(e)
        }
    }

    override suspend fun refreshResources(department: String, sem: Int, courseCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveSubject(subject: Subject) {
    }

    override suspend fun savePaper(paper: Paper) {
        TODO("Not yet implemented")
    }

    override suspend fun saveResource(resource: Resource) {
        TODO("Not yet implemented")
    }

}