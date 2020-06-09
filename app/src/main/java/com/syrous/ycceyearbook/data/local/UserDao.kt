package com.syrous.ycceyearbook.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.data.model.User


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun observeUser() : LiveData<User>

    @Query("SELECT * FROM user")
    suspend fun getUser() : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)


}