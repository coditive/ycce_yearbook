package com.syrous.ycceyearbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.model.Recent
import kotlinx.coroutines.flow.Flow


@Dao
interface RecentDao {

    @Query("SELECT * FROM papers JOIN recents ON papers.id = recents.id WHERE type = 'paper' ORDER BY hits DESC")
    fun observeRecentPapers(): Flow<List<Recent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecentPaper(recent: Recent)

    @Query("UPDATE recents SET hits = hits + 1 WHERE id =:paperId")
    suspend fun updateHits(paperId: String)
}