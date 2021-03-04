package com.syrous.ycceyearbook.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.syrous.ycceyearbook.model.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM departments")
    fun getDepartmentList(): Flow<List<Department>>

    @Query("UPDATE departments SET position = :position WHERE id = :departmentId")
    suspend fun updateDepartmentPosition(departmentId: Int, position: Int)

    @Transaction
    suspend fun swapDepartmentPositions(firstDepartmentId: Int, firstPosition: Int,
                                        secondDepartmentId: Int, secondPosition: Int) {
        updateDepartmentPosition(firstDepartmentId, secondPosition)
        updateDepartmentPosition(secondDepartmentId, firstPosition)
    }
}