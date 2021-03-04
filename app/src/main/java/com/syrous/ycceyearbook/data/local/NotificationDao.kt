package com.syrous.ycceyearbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.model.Notification
import kotlinx.coroutines.flow.Flow


@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications WHERE readState = :readState")
    fun observeReadNotification(readState: Int): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Notification)

    @Query("UPDATE notifications SET readState = 1 WHERE id= :notificationId")
    suspend fun updateNotificationReadState(notificationId: String)

    @Query("DELETE FROM notifications WHERE id= :notificationId")
    suspend fun deleteNotification(notificationId: String)
}