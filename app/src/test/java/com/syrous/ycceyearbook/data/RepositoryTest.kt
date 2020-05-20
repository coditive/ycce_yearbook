package com.syrous.ycceyearbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.syrous.ycceyearbook.data.model.Paper
import com.syrous.ycceyearbook.data.model.Resource
import com.syrous.ycceyearbook.data.model.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {
    private val paper1 = Paper("asdaGeqf", "ct", 3, "EVEN", "GE1001",
        "mse", "admin", "2019-20", "https://example.com", 10020030100)
    private val paper2 = Paper("asdaGvaeqf", "cv", 4, "ODD", "GE1002",
        "mse", "admin", "2019-20", "https://example.com", 10020032340)
    private val paper3 = Paper("asdaGeqasdf", "me", 5, "EVEN", "GE1003",
        "mse", "admin", "2019-20", "https://example.com", 10028900100)
    private val paper4 = Paper("asdaGasdeqf", "it", 6, "ODD", "GE1004",
        "mse", "admin", "2019-20", "https://example.com", 10020030230)

    private val paper5 = Paper("asdaGeqdasf", "ct", 3, "EVEN", "GE1001",
        "ese", "admin", "2019-20", "https://example.com", 10020030100)
    private val paper6 = Paper("asdaasdGvaeqf", "cv", 4, "ODD", "GE1002",
        "ese", "admin", "2019-20", "https://example.com", 10020032340)
    private val paper7 = Paper("aasdsdaGeqasdf", "me", 5, "EVEN", "GE1003",
        "ese", "admin", "2019-20", "https://example.com", 10028900100)
    private val paper8 = Paper("asdaGasdeasdqf", "it", 6, "ODD", "GE1004",
        "ese", "admin", "2019-20", "https://example.com", 10020030230)


    private var remotePapers = listOf(paper1, paper2, paper6, paper7, paper8, paper4)
    private var localPapers = listOf(paper2, paper3, paper5)
    private var totalPapers = listOf(paper1, paper2, paper6, paper7, paper8, paper4, paper2, paper3, paper5)

    private lateinit var localDataSource: FakeDataSource
    private lateinit var remoteDataSource: FakeDataSource

    private lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initialize_variables() {
       localDataSource = FakeDataSource(emptyList<Subject>().toMutableList(), localPapers.toMutableList(),
            emptyList<Resource>().toMutableList())

       remoteDataSource = FakeDataSource(emptyList<Subject>().toMutableList(), remotePapers.toMutableList(),
            emptyList<Resource>().toMutableList())

        repository = Repository(localDataSource, remoteDataSource, Dispatchers.Unconfined)
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
}