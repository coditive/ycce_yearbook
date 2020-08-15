package com.syrous.ycceyearbook.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject


@Dao
interface AllDao {

    @Query("SELECT * FROM subjects WHERE department = :department AND sem = :sem")
    fun observeSubjects(department: String, sem: Int): LiveData<List<Subject>>

    @Query("SELECT distinct sem FROM subjects WHERE department = :department ORDER BY sem ASC")
    fun observeSemester(department: String): LiveData<List<Int>>

    @Query("SELECT * FROM subjects WHERE department = :department AND sem = :sem")
    suspend fun getSubjects(department: String, sem: Int): List<Subject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Query("DELETE FROM subjects WHERE course_code = :course_code")
    suspend fun deleteSubject(course_code: String)

    @Query("SELECT * FROM papers WHERE department = :department AND sem = :sem AND courseCode = :courseCode AND exam = :exam")
    fun observePapers(department: String, sem: Int, courseCode: String, exam: String): LiveData<List<Paper>>

    @Query("SELECT * FROM papers WHERE department = :department AND sem = :sem AND courseCode = :courseCode AND exam = :exam")
    suspend fun getPapers(department: String, sem: Int, courseCode: String, exam: String): List<Paper>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaper(paper: Paper)

    @Query("DELETE FROM papers WHERE department = :department AND sem = :sem AND courseCode = :courseCode AND exam = :exam")
    suspend fun deletePapers(department: String, sem: Int, courseCode: String, exam: String)

    @Query("SELECT * FROM resources WHERE department = :department AND sem = :sem AND courseCode = :courseCode")
    fun observeResources(department: String, sem: Int, courseCode: String): LiveData<List<Resource>>

    @Query("SELECT * FROM resources WHERE department = :department AND sem = :sem AND courseCode = :courseCode")
    suspend fun getResources(department: String, sem: Int, courseCode: String): List<Resource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: Resource)

    @Query("DELETE FROM resources WHERE department = :department AND sem = :sem AND courseCode = :courseCode")
    suspend fun deleteResources(department: String, sem: Int, courseCode: String)

}