package com.syrous.ycceyearbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.syrous.ycceyearbook.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

class RepositoryTest {

    private var remotePapers = listOf(paper1, paper2, paper6, paper7, paper8, paper4)
    private var localPapers = listOf(paper2, paper3, paper5)
    private var totalPapers = listOf(paper1, paper2, paper6, paper7, paper8, paper4, paper2, paper3, paper5)

    private val remoteSubjects = listOf(subject1, subject2, subject4, subject7, subject8)
    private val localSubjects = listOf(subject3, subject5, subject6)
    private val totalSubjects = listOf(subject1, subject2, subject4, subject7, subject8, subject3, subject5, subject6)

    private val remoteResources = listOf<Resource>()
    private val localResources = listOf<Resource>()
    private val totalResources = listOf<Resource>()

    private lateinit var localDataSource: FakeDataSource
    private lateinit var remoteDataSource: FakeDataSource


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initialize_variables() {
       localDataSource = FakeDataSource(localSubjects.toMutableList(), localPapers.toMutableList(),
            localResources.toMutableList())

       remoteDataSource = FakeDataSource(remoteSubjects.toMutableList(), remotePapers.toMutableList(),
            remoteResources.toMutableList())

//        repository = Repository(localDataSource, remoteDataSource, Dispatchers.Unconfined)
    }

}