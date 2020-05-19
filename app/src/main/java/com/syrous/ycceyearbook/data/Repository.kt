package com.syrous.ycceyearbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syrous.ycceyearbook.data.local.AllDao
import com.syrous.ycceyearbook.data.local.LocalDataSource
import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.Result.Error
import com.syrous.ycceyearbook.data.model.Result.Success
import com.syrous.ycceyearbook.data.model.Subject
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: LibraryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun observeSubjects (department: String, sem: Int): LiveData<Result<List<Subject>>>{
        return localDataSource.observeSubjects(department, sem)
    }

    fun observePapers (department: String, sem:Int, courseCode: String, exam: String): LiveData<Result<List<Paper>>> {
        return localDataSource.observePapers(department, sem, courseCode, exam)
    }

    fun observeResources(department: String, sem: Int, courseCode: String): LiveData<Result<List<Resource>>> {
        return localDataSource.observeResources(department, sem, courseCode)
    }

    suspend fun refreshSubjects(department: String, sem: Int) {
        updateSubjectsFromRemoteDataSource(department, sem)
    }

    suspend fun refreshPapers(department: String, sem: Int, courseCode: String, exam: String) {
        updatePapersFromRemoteDataSource(department, sem, courseCode, exam)
    }

    suspend fun refreshResources(department: String, sem: Int, courseCode: String) {
        updateResourcesFromRemoteDataSource(department, sem, courseCode)
    }

    private suspend fun updateSubjectsFromRemoteDataSource(department: String, sem: Int){
        val subjects = remoteDataSource.getSubjects(department, sem)

        if (subjects is Success){
            subjects.data.forEach {subject ->
                localDataSource.saveSubject(subject)
            }
        } else if (subjects is Error) {
            throw subjects.exception
        }
    }

    private suspend fun updatePapersFromRemoteDataSource(department: String, sem: Int, courseCode: String, exam: String){
        val papers = remoteDataSource.getPapers(department, sem, courseCode, exam)

        if (papers is Success){
            papers.data.forEach {paper ->
                localDataSource.savePaper(paper)
            }
        } else if (papers is Error) {
            throw papers.exception
        }
    }

    private suspend fun updateResourcesFromRemoteDataSource(department: String, sem: Int, courseCode: String){
        val resources = remoteDataSource.getResources(department, sem, courseCode)

        if (resources is Success){
            resources.data.forEach {resource ->
                localDataSource.saveResource(resource)
            }
        } else if (resources is Error) {
            throw resources.exception
        }
    }
}