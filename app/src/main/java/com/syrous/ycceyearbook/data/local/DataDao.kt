package com.syrous.ycceyearbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject
import kotlinx.coroutines.flow.Flow


@Dao
interface DataDao {

    @Query("SELECT * FROM subjects WHERE department = :department AND sem = :sem")
    fun observeSubjects(department: String, sem: Int): Flow<List<Subject>>

    @Query("SELECT distinct sem FROM subjects WHERE department = :department ORDER BY sem ASC")
    fun observeSemester(department: String): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    @Query("DELETE FROM subjects WHERE course_code = :course_code")
    suspend fun deleteSubject(course_code: String)

    @Query("SELECT * FROM papers WHERE department = :department AND sem = :sem AND courseCode = :courseCode AND exam = :exam")
    fun observePapers(department: String, sem: Int, courseCode: String, exam: String): Flow<List<Paper>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPaper(paper: Paper)

    @Query("DELETE FROM papers WHERE department = :department AND sem = :sem AND courseCode = :courseCode AND exam = :exam")
    suspend fun deletePapers(department: String, sem: Int, courseCode: String, exam: String)

    @Query("SELECT * FROM resources WHERE department = :department AND sem = :sem AND courseCode = :courseCode")
    fun observeResources(department: String, sem: Int, courseCode: String): Flow<List<Resource>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: Resource)

    @Query("DELETE FROM resources WHERE department = :department AND sem = :sem AND courseCode = :courseCode")
    suspend fun deleteResources(department: String, sem: Int, courseCode: String)
}