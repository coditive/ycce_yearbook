package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.DataAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.DataDao
import com.syrous.ycceyearbook.data.remote.RemoteApi
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Semester
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val _esePaperData = MutableStateFlow(emptyList<Paper>())
    val esePaperData: StateFlow<List<Paper>> = _esePaperData

    private val _msePaperData = MutableStateFlow(emptyList<Paper>())
    val msePaperData: StateFlow<List<Paper>> = _msePaperData

    private val _resourceData = MutableStateFlow(emptyList<Resource>())
    val resourceData: StateFlow<List<Resource>> = _resourceData

    private val _semesterData = MutableStateFlow(emptyList<Semester>())
    val semesterData: StateFlow<List<Semester>> = _semesterData

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        Timber.d("CoroutineContext: $coroutineContext")
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .filterIsInstance<DataAction>()
                .combine(networkStore.isConnected) { dataAction, isConnected ->
                    Timber.d("Network State: $isConnected")
                    when(dataAction){
                        is DataAction.GetEseData -> fetchEsePaperDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetMseData -> fetchMsePaperDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetResource -> fetchResourceDataAndStore(isConnected,
                            dataAction.department, dataAction.sem, dataAction.courseCode)
                        is DataAction.GetSubjects -> fetchSubjectDataAndStore(isConnected,
                            dataAction.department)
                        is DataAction.GetSemester -> fetchSemesterForDepartment(dataAction.department)
                    }
                }.collect {
                }
        }
    }

    private suspend fun fetchSemesterForDepartment(
        department: String) {
        _loading.emit(true)
        try {
            val semesterData = mutableListOf<Semester>()
            val semesterList = dataDao.getSemesterList(department)
            for (sem in semesterList) {
                val subjectList = dataDao.getSubjects(department, sem)
                semesterData.add(Semester(department, sem, subjectList))
            }
            _semesterData.emit(semesterData)
        } catch (e: Exception) {
            Timber.e(e)
            dispatcher.dispatch(SentryAction(e))
        } finally {
            _loading.emit(false)
        }
    }

    private suspend fun fetchEsePaperDataAndStore(
        isConnected: Boolean,
        department: String,
        sem: Int,
        courseCode: String) {
       _loading.emit(true)
        try {
            val paperList = remoteApi.getEsePapers(department, sem, courseCode)
            for (paper in paperList) {
                dataDao.insertPaper(paper)
            }
            _esePaperData.emit(paperList)
        } catch (e: Exception) {
            dispatcher.dispatch(SentryAction(e))
        }
        _loading.emit(false)
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
                _msePaperData.emit(paperList)
            } catch (e: Exception) {
                dispatcher.dispatch(SentryAction(e))
            }

        } else {
            dataDao.observePapers(department, sem, courseCode, "mse").collect {
                    papers ->
                _msePaperData.emit(papers)
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


    private suspend fun fetchSubjectDataAndStore(
        isConnected: Boolean,
        department: String) {
            _loading.emit(true)
            try {
                val subjectList = mutableListOf<Subject>()
                if (department == "fy") {
                    for (sem in 1..2) {
                        val list = remoteApi.getSubjects(department, sem)
                        for (subject in list) {
                            subjectList.add(subject)
                        }
                    }
                } else {
                    for (sem in 3..8) {
                        val list = remoteApi.getSubjects(department, sem)
                        for (subject in list) {
                            subjectList.add(subject)
                        }
                    }
                }
                for(subject in subjectList) {
                    dataDao.insertSubject(subject)
                }
            } catch (e: Exception) {
                Timber.e(e)
                dispatcher.dispatch(SentryAction(e))
            } finally {
                _loading.emit(false)
                dispatcher.dispatch(DataAction.GetSemester(department))
            }

    }

}