package com.syrous.ycceyearbook.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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

class LocalDataSource @Inject constructor(
    private val allDao: AllDao
): LibraryDataSource {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun observeSubjects(department: String, sem: Int): LiveData<Result<List<Subject>>> {
        return allDao.observeSubjects(department, sem).map {
            Success(it)
        }
    }

    suspend fun getSemesterFromLocal(department: String): Result<List<Int>> {
        val result = allDao.getSemester(department)
        return Success(result)
    }

    suspend fun getSubjectFromLocal(department: String, sem: Int): Result<List<Subject>> {
        val result = allDao.getSubjects(department, sem)
        return Success(result)
    }

    override fun observePapers(department: String, sem: Int, courseCode: String, exam: String): LiveData<Result<List<Paper>>> {
        return allDao.observePapers(department, sem, courseCode, exam).map {
            Success(it)
        }
    }

    override fun observeResources(department: String, sem: Int, courseCode: String): LiveData<Result<List<Resource>>> {
       return allDao.observeResources(department, sem, courseCode).map {
           Success(it)
       }
    }

    override suspend fun getSubjects(department: String, sem: Int): Result<List<Subject>> {
        return withContext(ioDispatcher) {
            try {
                val list = allDao.getSubjects(department, sem)
                Success(list)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    override suspend fun refreshSubjects(department: String, sem: Int) {
        //No - Operation
    }

    override suspend fun getPapers(department: String, sem: Int, courseCode: String, exam: String): Result<List<Paper>> {
        return withContext(ioDispatcher) {
            try {
                val papers = allDao.getPapers(department, sem, courseCode, exam)
                Success(papers)
            } catch (e: Exception) {
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
        //No - Operation
    }

    override suspend fun getResources(department: String, sem: Int, courseCode: String): Result<List<Resource>> {
        return withContext(ioDispatcher) {
            try {
                val resources = allDao.getResources(department, sem, courseCode)
                Success(resources)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    override suspend fun refreshResources(department: String, sem: Int, courseCode: String) {
        //No - Operation
    }

    override suspend fun saveSubject(subject: Subject) = withContext (ioDispatcher) {
        allDao.insertSubject(subject)
    }

    override suspend fun savePaper(paper: Paper) = withContext (ioDispatcher) {
        allDao.insertPaper(paper)
    }

    override suspend fun saveResource(resource: Resource) = withContext (ioDispatcher) {
        allDao.insertResource(resource)
    }
}