package com.syrous.ycceyearbook.data

import androidx.lifecycle.LiveData
import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.Subject

class FakeDataSource (
    var subjects: MutableList<Subject>? = mutableListOf(),
    var papers: MutableList<Paper>? = mutableListOf(),
    var resources: MutableList<Resource>? = mutableListOf()
) : LibraryDataSource {
    override fun observeSubjects(department: String, sem: Int): LiveData<Result<List<Subject>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjects(department: String, sem: Int): Result<List<Subject>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshSubjects(department: String, sem: Int) {
        TODO("Not yet implemented")
    }

    override fun observePapers(
        department: String,
        sem: Int,
        courseCode: String,
        exam: String
    ): LiveData<Result<List<Paper>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPapers(
        department: String,
        sem: Int,
        courseCode: String,
        exam: String
    ): Result<List<Paper>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPapers(
        department: String,
        sem: Int,
        courseCode: String,
        exam: String
    ) {
        TODO("Not yet implemented")
    }

    override fun observeResources(
        department: String,
        sem: Int,
        courseCode: String
    ): LiveData<Result<List<Resource>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getResources(
        department: String,
        sem: Int,
        courseCode: String
    ): Result<List<Resource>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshResources(department: String, sem: Int, courseCode: String) {
        TODO("Not yet implemented")
    }

}