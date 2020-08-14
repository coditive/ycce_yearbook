package com.syrous.ycceyearbook.ui.semester

import androidx.lifecycle.*
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SemVM @Inject constructor(
    private val repository: Repository)
    : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _forceUpdate = MutableLiveData(false)

    private val _subjectList = MutableLiveData<List<Subject>>()
    val subjectList: LiveData<List<Subject>> = _subjectList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    private val _isDataLoadingError = MutableLiveData<Boolean>(false)
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val listOfSubjectList = mutableListOf<List<Subject>>(emptyList(), emptyList(),
        emptyList(), emptyList(), emptyList(), emptyList(),
        emptyList(), emptyList())

    fun reloadSubjectFromRemote(department: String, sem: Int) {
        viewModelScope.launch {
            repository.refreshSubjects(department, sem)
        }
    }

    private val toggleSemesterState  = mutableListOf(false, false, false, false, false, false, false, false)

    fun getSubjectFromLocalStorageInVM(department: String, minSem: Int, maxSem: Int) {
        _loading.value = true

        Timber.d("ViewModel initialization called!!!")
        for(i in 1..8) {
            when {
                i < minSem -> {
                    listOfSubjectList[i-1] = (emptyList())
                }
                i in minSem..maxSem -> {
                    viewModelScope.launch {
                        listOfSubjectList[i-1] = (filterSubject(repository.getSubjectsFromLocalStorage(department, i)))
                        Timber.d("listofSubjects $listOfSubjectList" )
                    }
                }
                else -> {
                    listOfSubjectList[i-1] = (emptyList())
                }
            }

        }
        _loading.value = false
    }

    private fun filterSubject(subjectResult: Result<List<Subject>>)
            : List<Subject>  {
        var result = listOf<Subject>()
        if(subjectResult is Success){
            result = subjectResult.data
        } else if(subjectResult is Error) {
            _isDataLoadingError.value = true
             _errorMessage.value = "Failed to retrieve subjects ${subjectResult.exception}"
        }
        return result
    }

    fun getSubjectsForSemester(sem: Int) : List<Subject> {
       return if(!toggleSemesterState[sem - 1]) {
            listOfSubjectList[sem - 1]
        } else {
           emptyList()
       }
    }


}