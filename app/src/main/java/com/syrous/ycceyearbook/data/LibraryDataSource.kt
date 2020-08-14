package com.syrous.ycceyearbook.data

import androidx.lifecycle.LiveData
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Subject

interface LibraryDataSource {

    fun observeSubjects(department: String, sem: Int): LiveData<Result<List<Subject>>>

    suspend fun getSubjects(department: String, sem: Int): Result<List<Subject>>

    suspend fun refreshSubjects(department: String, sem: Int)

    fun observePapers(department: String, sem: Int, courseCode: String, exam: String): LiveData<Result<List<Paper>>>

    suspend fun getPapers(department: String, sem: Int, courseCode: String, exam: String): Result<List<Paper>>

    suspend fun refreshPapers(department: String, sem: Int, courseCode: String, exam: String)

    fun observeResources(department: String, sem: Int, courseCode: String): LiveData<Result<List<Resource>>>

    suspend fun getResources(department: String, sem: Int, courseCode: String): Result<List<Resource>>

    suspend fun refreshResources(department: String, sem: Int, courseCode: String)

    suspend fun saveSubject(subject: Subject)

    suspend fun savePaper(paper: Paper)

    suspend fun saveResource(resource: Resource)
}