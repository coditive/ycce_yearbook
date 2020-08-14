package com.syrous.ycceyearbook.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syrous.ycceyearbook.data.LibraryDataSource
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remoteApi: RemoteApi
): LibraryDataSource {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val observableSubjects = MutableLiveData<Result<List<Subject>>>()
    private val observablePapers = MutableLiveData<Result<List<Paper>>>()
    private val observableResources = MutableLiveData<Result<List<Resource>>>()


    override fun observeSubjects(department: String, sem: Int): LiveData<Result<List<Subject>>> {
        return observableSubjects
    }

    override suspend fun getSubjects(department: String, sem: Int): Result<List<Subject>> {
        return withContext(ioDispatcher){
            try {
                val subjects = remoteApi.getSubjects(department, sem)
                Success(subjects)
            } catch (e: Exception){
                Error(e)
            }
        }
    }

    override suspend fun refreshSubjects(department: String, sem: Int) {
        observableSubjects.value = getSubjects(department, sem)!!
    }

    override fun observePapers(department: String, sem: Int, courseCode: String, exam: String): LiveData<Result<List<Paper>>> {
        return observablePapers
    }

    override suspend fun getPapers(department: String, sem: Int, courseCode: String, exam: String): Result<List<Paper>> {
        return withContext(ioDispatcher){
            try {
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
    }

    override suspend fun refreshPapers(
        department: String,
        sem: Int,
        courseCode: String,
        exam: String
    ) {
        observablePapers.value = getPapers(department, sem, courseCode, exam)!!
    }

    override fun observeResources(department: String, sem: Int, courseCode: String): LiveData<Result<List<Resource>>> {
        return observableResources
    }

    override suspend fun getResources(department: String, sem: Int, courseCode: String): Result<List<Resource>> {
        return withContext(ioDispatcher){
            try {
                val resources = remoteApi.getResource(department, sem, courseCode)
                Success(resources)
            } catch (e: Exception){
                Error(e)
            }
        }
    }

    override suspend fun refreshResources(department: String, sem: Int, courseCode: String) {
        observableResources.value = getResources(department, sem, courseCode)!!
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