package com.syrous.ycceyearbook.data.local

import androidx.room.Dao
import androidx.room.Query
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource


@Dao
interface RecentDao {

    @Query("SELECT * FROM papers JOIN recents ON papers.id = recents.id WHERE type MATCH 'paper'")
    suspend fun observeRecentPapers(): List<Paper>

    @Query("SELECT * FROM resources JOIN recents ON resources.id = recents.id WHERE type MATCH 'resource'")
    suspend fun observeRecentResources(): List<Resource>

}