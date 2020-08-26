package com.syrous.ycceyearbook.ui.semester

import androidx.lifecycle.*
import com.syrous.ycceyearbook.data.Repository
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

    fun loadListOfSemestersFromLocal(lifecycleOwner: LifecycleOwner, department: String) {
            repository.loadListOfSubjectsFromRepo(lifecycleOwner,department)
    }

    fun toggleChildVisibility(department: String, sem: Int, index: Int) {
        viewModelScope.launch {
            repository.toggleSubjectVisibility(department, sem, index)
        }
    }
}