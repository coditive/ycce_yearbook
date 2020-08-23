package com.syrous.ycceyearbook.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Recent
import com.syrous.ycceyearbook.model.Resource


@Dao
interface RecentDao {

    @Query("SELECT * FROM papers JOIN recents ON papers.id = (SELECT recents.id FROM recents WHERE type = 'paper' ORDER BY hits DESC)")
    fun observeRecentPapers(): LiveData<List<Paper>>

    @Query("SELECT * FROM resources JOIN recents ON resources.id = recents.id WHERE type MATCH 'resource'")
    suspend fun observeRecentResources(): List<Resource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentPaper(recent: Recent)

    @Query("SELECT * FROM recents WHERE id = :recentId")
    suspend fun getRecent(recentId: String): List<Recent>

    @Query("UPDATE recents SET hits = hits + 1 WHERE id =:recentId")
    suspend fun updateHits(recentId: String)
}