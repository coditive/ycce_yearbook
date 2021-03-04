package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.DataDao
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
class DataStore constructor(
    private val dispatcher: Dispatcher,
    private val networkStore: NetworkStore,
    private val dataDao: DataDao,
    private val remoteApi: RemoteApi,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    private val _paperData = MutableStateFlow(emptyList<Paper>())
    val paperData: StateFlow<List<Paper>> = _paperData

    private val _resourceData = MutableStateFlow(emptyList<Resource>())
    val resourceData: StateFlow<List<Resource>> = _resourceData

    private val _subjectData = MutableStateFlow(emptyList<Subject>())
    val subjectData: StateFlow<List<Subject>> = _subjectData

    private val _errorData = MutableStateFlow("")
    val errorData: StateFlow<String> = _errorData

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<DataAction>()
                .combine(networkStore.isConnected) { dataAction, isConnected ->
                    when(dataAction){
                        is DataAction.GetEseData -> fetchEsePaperDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetMseData -> fetchMsePaperDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetResource -> fetchResourceDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetSubjects -> fetchSubjectDataAndStore(isConnected,
                            dataAction.department, dataAction.sem)
                    }
                }.collect {  }
        }
    }

    private suspend fun fetchEsePaperDataAndStore(
        isConnected: Boolean,
        department: String,
        sem: Int,
        courseCode: String) =
        if(isConnected) {
            try {
                val paperList = remoteApi.getEsePapers(department, sem, courseCode)
                for(paper in paperList) {
                    dataDao.insertPaper(paper)
                }
                _paperData.emit(paperList)
            } catch (e: Exception) {
                dispatcher.dispatch(SentryAction(e))
            }
        } else {
            dataDao.observePapers(department, sem, courseCode, "ese").collect {
                    papers ->
                _paperData.emit(papers)
            }
        }


    private suspend fun fetchMsePaperDataAndStore(
        isConnected: Boolean,
        department: String,
        sem: Int,
        courseCode: String) =
        if(isConnected) {
            try {
                val paperList = remoteApi.getMsePapers(department, sem, courseCode)
                for(paper in paperList) {
                    dataDao.insertPaper(paper)
                }
                _paperData.emit(paperList)
            } catch (e: Exception) {
                dispatcher.dispatch(SentryAction(e))
            }

        } else {
            dataDao.observePapers(department, sem, courseCode, "mse").collect {
                    papers ->
                _paperData.emit(papers)
            }
        }


    private suspend fun fetchResourceDataAndStore(
        isConnected: Boolean,
        department: String,
        sem: Int,
        courseCode: String) =
        if(isConnected) {
            try {
                val resourceList = remoteApi.getResource(department, sem, courseCode)
                for(resource in resourceList) {
                    dataDao.insertResource(resource)
                }
                _resourceData.emit(resourceList)
            } catch (e: Exception) {
                dispatcher.dispatch(SentryAction(e))
            }

        } else {
            dataDao.observeResources(department, sem, courseCode).collect {
                    resources ->
                _resourceData.emit(resources)
            }
        }


    private fun fetchSubjectDataAndStore(
        isConnected: Boolean,
        department: String,
        sem: Int) =
        coroutineScope.launch {
            if(isConnected) {
                try {
                    val subjectList = remoteApi.getSubjects(department, sem)
                    for(subject in subjectList) {
                        dataDao.insertSubject(subject)
                    }
                    _subjectData.emit(subjectList)
                } catch (e: Exception) {
                    dispatcher.dispatch(SentryAction(e))
                }

            } else {
                dataDao.observeSubjects(department, sem).collect {
                        subjects ->
                    _subjectData.emit(subjects)
                }
            }
        }


}