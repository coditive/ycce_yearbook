package com.syrous.ycceyearbook.data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.syrous.ycceyearbook.data.local.LocalDataSource
import com.syrous.ycceyearbook.data.remote.RemoteDataSource
import com.syrous.ycceyearbook.model.*
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    private val _listOfSubjectList = MutableLiveData<List<List<SemSubModel>>>()
    val listOfSubjectList: LiveData<List<List<SemSubModel>>> = _listOfSubjectList

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    val listOfRecentPapers = localDataSource.observeRecentPapers()

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
        _dataLoading.value = true
        withContext(Dispatchers.IO) {
            val subjects = remoteDataSource.getSubjects(department, sem)
            if (subjects is Success) {
                subjects.data.forEach { subject ->
                    localDataSource.saveSubject(subject)
                }
            } else if (subjects is Error) {
                throw subjects.exception
            }
            delay(200)
            _dataLoading.postValue(false)
        }
    }

    private suspend fun updatePapersFromRemoteDataSource(department: String, sem: Int, courseCode: String, exam: String){
        _dataLoading.value = true
        withContext(Dispatchers.IO) {
            val papers = remoteDataSource.getPapers(department, sem, courseCode, exam)

            if (papers is Success){
                papers.data.forEach {paper ->
                    localDataSource.savePaper(paper)
                    Timber.d("paper list from remote : $paper")
                }
            } else if (papers is Error) {
                throw papers.exception
            }
            delay(200)
            _dataLoading.postValue(false)
        }
    }

    private suspend fun updateResourcesFromRemoteDataSource(department: String, sem: Int, courseCode: String){
        _dataLoading.value = true
        withContext(Dispatchers.IO) {
            val resources = remoteDataSource.getResources(department, sem, courseCode)
            if (resources is Success){
                resources.data.forEach {resource ->
                    localDataSource.saveResource(resource)
                }
            } else if (resources is Error) {
                throw resources.exception
            }
            delay(200)
            _dataLoading.postValue(false)
        }
    }

    suspend fun getPapersFromLocalStorage(department: String, sem:Int, courseCode: String, exam: String): Result<List<Paper>> {
        return localDataSource.getPapers(department, sem, courseCode, exam)
    }

    suspend fun getResourcesFromLocalStorage(department: String, sem: Int, courseCode: String): Result<List<Resource>> {
        return localDataSource.getResources(department, sem, courseCode)
    }

    fun loadListOfSubjectsFromRepo(lifeCycleOwner: LifecycleOwner, department: String) {
        localDataSource.observeSemesterFromLocal(department).observe(lifeCycleOwner) {
           _listOfSubjectList.postValue(filterAndCreateSemester(it))
        }
    }


    private fun filterAndCreateSemester(semesterResult: Result<List<Int>>): List<List<SemSubModel>>  {
        var result = emptyList<List<SemSubModel>>()
        if(semesterResult is Success){
            val listOfSemesterList = mutableListOf<List<SemSubModel>>()
            semesterResult.data.forEach { i ->
                val semSubList = mutableListOf<SemSubModel>()
                val semester = Semester("Semester $i", i)
                Timber.d("$semester Created")
                semSubList.add(semester as SemSubModel)
                listOfSemesterList.add(semSubList)
            }
            result = listOfSemesterList
        } else if(semesterResult is Error) {
            TODO(" Display error")
        }
        return result
    }

    suspend fun toggleSubjectVisibility(department: String, sem: Int, index: Int) {
        withContext(Dispatchers.IO) {
            val semSubList = (_listOfSubjectList.value as List<List<SemSubModel>>).toMutableList()
            val subjectList = semSubList[index].toMutableList()
            if (subjectList.size == 1) {
                val result = localDataSource.getSubjectFromLocal(department, sem)
                    Timber.d("Subject List: $result ,Added as index: $index")
                    filterSubject(result).forEach { subject ->
                        subjectList.add(subject)

                    Timber.d("Subject list Size: ${subjectList.size}")
                    Timber.d("SemSub List Size: ${semSubList.size}")
                }
            } else {
                val size = subjectList.size - 1
                Timber.d("Size of Subject List : $size")
                for (i in size downTo 1) {
                    Timber.d("Removed Subject ${subjectList[i]}")
                    subjectList.removeAt(i)
                }
            }

            semSubList[index] = subjectList
            _listOfSubjectList.postValue(semSubList)
        }
    }

    private fun filterSubject(subjectResult: Result<List<Subject>>): List<SemSubModel>  {
        val result = mutableListOf<SemSubModel>()
        if(subjectResult is Success){
            subjectResult.data.forEach {
                result.add(it as SemSubModel)
                Timber.d("Subject added : $it")
            }
        } else if(subjectResult is Error) {
            TODO(" Display error")
        }
        return result
    }

    suspend fun saveOrUpdateRecentlyUsedPaper(paper: Paper) {
        withContext(Dispatchers.IO) {
            val result = localDataSource.getRecentsObject(paper.id)
            if(result is Success) {
              if(result.data.isEmpty()) {
                  val recent = Recent(paper.id, "paper", 1)
                  localDataSource.saveRecentlyUsedPaper(recent)
              } else {
                  localDataSource.updateHits(paper.id)
              }
            }
        }
    }

}
