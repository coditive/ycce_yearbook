package com.syrous.ycceyearbook.ui.papers_and_resources

import android.content.Context
import androidx.lifecycle.*
import com.google.firebase.storage.FirebaseStorage
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class PaperAndResourceVM @Inject constructor(private val repository: Repository)
    : ViewModel() {

    private val _forceUpdateResource = MutableLiveData(false)

    private val _forceUpdateEse = MutableLiveData(false)

    private val _forceUpdateMse = MutableLiveData(false)

    private val _loading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _loading

    private val _isDataLoadingError = MutableLiveData<Boolean>(false)
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage


    fun reloadEsePaperFromRemote(forceUpdate: Boolean) {
        _forceUpdateEse.value = forceUpdate
    }

    fun reloadMsePaperFromRemote(forceUpdate: Boolean) {
        _forceUpdateMse.value = forceUpdate
    }

    fun reloadResourceFromRemote(forceUpdate: Boolean) {
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
            : LiveData<List<Paper>> = _forceUpdateMse.switchMap { forceUpdate ->
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

    suspend fun storeRecentlyUsedPaper(context: Context, paper: Paper): File {
        _loading.postValue(true)
        repository.saveOrUpdateRecentlyUsedPaper(paper)
        val path = context.getExternalFilesDir("papers")
        val paperFile = File(path, "paper${paper.id}.pdf")
        val storage = FirebaseStorage.getInstance()
        val refs = storage.getReferenceFromUrl(paper.url)
        refs.getFile(paperFile)
        delay(2000)
        Timber.d("File path : ${paperFile.name}, ${paperFile.absoluteFile}, Total Space: ${paperFile.totalSpace}")
        _loading.postValue(false)
        return paperFile
    }

    private fun filterPaper(paperResult: Result<List<Paper>>)
            : List<Paper>  {
        var result = listOf<Paper>()
        if(paperResult is Success){
            result = paperResult.data
        } else if(paperResult is Error) {
            _isDataLoadingError.value = true
            _errorMessage.value = "Failed to Retrieve Papers -> ${paperResult.exception}"
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
            _errorMessage.value = "Failed to Retrieve Resources ->  ${resourceResult.exception}"
        }
        return result
    }

}