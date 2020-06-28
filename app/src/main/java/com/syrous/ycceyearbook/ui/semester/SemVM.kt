package com.syrous.ycceyearbook.ui.semester

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.Result.Error
import com.syrous.ycceyearbook.data.model.Result.Success
import com.syrous.ycceyearbook.data.model.Subject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SemVM @Inject constructor(
    private val repository: Repository)
    : ViewModel() {

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean>
    get() = _dataLoading

    private val _forceUpdate = MutableLiveData(false)

    private val _subjectList = MutableLiveData<List<Subject>>()
    val subjectList: LiveData<List<Subject>>
    get() = _subjectList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String>
    get() = _errorMessage

    private val _isDataLoadingError = MutableLiveData<Boolean>(false)
    val isDataLoadingError: LiveData<Boolean>
    get() = _isDataLoadingError



    fun loadAllSubjects(forceUpdate: Boolean, department: String) {
        if(forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                for(i in 3..8){
                    repository.refreshSubjects(department, i)
                }
            }
            _dataLoading.value = false
        }
    }

    fun getSubjectList(department: String, sem: Int): List<Subject> = runBlocking {
        _dataLoading.value = true
       try {
           val result = repository.getSubjectsFromLocalStorage(department , sem)
           filterSubject(result)
        } catch (e: Exception) {
            _errorMessage.value = "Error Occurred while Fetching data!!!"
             emptyList<Subject>()
        } finally {
            _dataLoading.value = false
        }
    }

    @Throws(Exception::class)
    private fun filterSubject(subjectResult: Result<List<Subject>>)
            : List<Subject>  {
        var result = listOf<Subject>()
        if(subjectResult is Success){
            result = subjectResult.data as List<Subject>
        } else if(subjectResult is Error) {
            _isDataLoadingError.value = true
             throw subjectResult.exception
        }
        return result
    }



}