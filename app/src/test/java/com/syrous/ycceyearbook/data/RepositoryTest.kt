package com.syrous.ycceyearbook.data

import com.syrous.ycceyearbook.data.model.Paper
import org.junit.Assert.*

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


    private val remotePapers = listOf(paper1, paper2, paper6)
    private val localPapers = listOf(paper2, paper3, paper5)


}