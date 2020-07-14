package com.syrous.ycceyearbook.ui.papers_and_resources

import androidx.lifecycle.*
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Result
import com.syrous.ycceyearbook.data.model.Result.Error
import com.syrous.ycceyearbook.data.model.Result.Success
import kotlinx.coroutines.launch
import javax.inject.Inject


class PaperAndResourceVM @Inject constructor(private val repository: Repository)
    : ViewModel() {

    private val _forceUpdateResource = MutableLiveData(false)

    private val _forceUpdateEse = MutableLiveData(false)

    private val _forceUpdateMse = MutableLiveData(false)

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _isDataLoadingError = MutableLiveData<Boolean>(false)
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage


    fun loadEsePaper(forceUpdate: Boolean) {
        _forceUpdateEse.value = forceUpdate
    }

    fun loadMsePaper(forceUpdate: Boolean) {
        _forceUpdateMse.value = forceUpdate
    }

    fun loadResource(forceUpdate: Boolean) {
        _forceUpdateResource.value = forceUpdate
    }

    fun observeResource(department: String, sem: Int, courseCode: String)
            : LiveData<List<Resource>> = _forceUpdateResource.switchMap { forceUpdate ->
            if(forceUpdate) {
                _loading.value = true
                viewModelScope.launch {
                    repository.refreshResources(department, sem, courseCode)
                }
                _loading.value = false
            }
            repository.observeResources(department, sem, courseCode).map {
                filterResource(it)
            }
        }


    fun observeEsePaper(department: String, sem: Int, courseCode: String)
            : LiveData<List<Paper>> = _forceUpdateEse.switchMap { forceUpdate ->
        if(forceUpdate) {
            _loading.value = true
            viewModelScope.launch {
                repository.refreshPapers(department, sem, courseCode, "ese")
            }
            _loading.value = false
        }

        repository.observePapers(department, sem, courseCode, "ese").map {
            filterPaper(it)
        }
    }

    fun observeMsePaper(department: String, sem: Int, courseCode: String)
            : LiveData<List<Paper>> = _forceUpdateMse.switchMap {forceUpdate ->
        if(forceUpdate) {
            _loading.value = true
            viewModelScope.launch {
                repository.refreshPapers(department, sem, courseCode, "mse")
            }
            _loading.value = false
        }
        repository.observePapers(department, sem, courseCode, "mse").map {
            filterPaper(it)
        }
    }

    private fun filterPaper(paperResult: Result<List<Paper>>)
            : List<Paper>  {
        var result = listOf<Paper>()
        if(paperResult is Success){
            result = paperResult.data
        } else if(paperResult is Error) {
            _isDataLoadingError.value = true
            _errorMessage.value = "Failed to Retrieve Papers"
        }
        return result
    }

    private fun filterResource(resourceResult: Result<List<Resource>>)
            : List<Resource>  {
        var result = listOf<Resource>()
        if(resourceResult is Success){
            result = resourceResult.data
        } else if(resourceResult is Error) {
            _isDataLoadingError.value = true
            _errorMessage.value = "Failed to Retrieve Resources"
        }
        return result
    }

}