package com.syrous.ycceyearbook.ui.semester

import androidx.lifecycle.*
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.Result.Error
import com.syrous.ycceyearbook.data.model.Result.Success
import com.syrous.ycceyearbook.data.model.Subject
import kotlinx.coroutines.launch
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


    fun reloadSubjectFromRemote(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun observeSubjectFromLocalStorage(department: String, sem: Int)
            : LiveData<List<Subject>> = _forceUpdate.switchMap {forceUpdate ->
        if(forceUpdate) {
            _loading.value = true
            viewModelScope.launch {
                repository.refreshSubjects(department, sem)
            }
            _loading.value = false
        }

        repository.observeSubjects(department, sem).map {
                filterSubject(it)
        }
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

}