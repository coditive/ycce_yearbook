package com.syrous.ycceyearbook.ui.semester

import androidx.lifecycle.*
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.SemSubModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SemVM @Inject constructor(
    private val repository: Repository,
    val department: String)
    : ViewModel() {

    val dataLoading: LiveData<Boolean> = repository.dataLoading

    private val _forceUpdate = MutableLiveData(false)

    val subjectList: LiveData<List<List<SemSubModel>>> = repository.listOfSubjectList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    private val _isDataLoadingError = MutableLiveData<Boolean>(false)
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    fun reloadSubjectFromRemote(department: String, sem: Int) {
        viewModelScope.launch {
            repository.refreshSubjects(department, sem)
        }
    }

    fun loadListOfSemestersFromLocal(department: String): LiveData<List<Int>> {
        return repository.observeSemesters(department).map {
            filterSemester(it)
        }
    }


    fun loadListOfSubjects(lifeCycleOwner: LifecycleOwner, department: String) {
        repository.loadListOfSubjects(lifeCycleOwner, department)
    }

    fun toggleChildVisibility(lifeCycleOwner: LifecycleOwner, department: String, sem: Int, index: Int) {
        repository.toggleSubjectVisibility(lifeCycleOwner, department, sem, index)
    }


    private fun filterSemester(semesterResult: Result<List<Int>>)
            : List<Int>  {
        val result = mutableListOf<Int>()
        if(semesterResult is Success){
           semesterResult.data.forEach {
               result.add(it)
           }
        } else if(semesterResult is Error) {
            TODO(" Display error")
        }
        return result
    }
}