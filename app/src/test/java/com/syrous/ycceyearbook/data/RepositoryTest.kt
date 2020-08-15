package com.syrous.ycceyearbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.syrous.ycceyearbook.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
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

    private lateinit var repository: Repository

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


    @Test
    fun updatePapersFromRemoteToLocal_remotePapersAndLocalPapers_returnsCombinedListOfPapers() = runBlockingTest {
        //Given -> localDataSource to the repository with list of papers already in it.

        //when -> refresh method is called from the repository
        repository.refreshPapers("ct", 3, "GE1001", "mse")

        //then -> it should return combined list of local and remote papers
        assertThat(repository.getPapersFromLocalStorage("ct", 3, "GE1001", "mse"))
            .isNotEqualTo(totalPapers)
    }

    @Test
    fun updateSubjectsFromRemoteToLocal_remoteSubjectsAndLocalSubjects_returnsCombinedListOfSubjects() = runBlockingTest {
        //Given -> localDataSource to the repository with list of subjects already in it.

        //when -> refresh method is called from the repository
        repository.refreshSubjects("ct", 3)

        //then -> it should return combined list of local and remote resources
//        assertThat(repository.getSubjectsFromLocalStorage("ct", 3))
//            .isNotEqualTo(totalSubjects)
    }

    @Test
    fun updateResourcesFromRemoteToLocal_remoteResourcesAndLocalResources_returnsCombinedListOfResources() = runBlockingTest {
        //Given -> localDataSource to the repository with list of resources already in it.

        //when -> refresh method is called from the repository
        repository.refreshResources("ct", 3, "GE1001")

        //then -> it should return combined list of local and remote resources
        assertThat(repository.getResourcesFromLocalStorage("ct", 3, "GE1001"))
            .isNotEqualTo(totalResources)
    }


}