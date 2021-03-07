package com.syrous.ycceyearbook.model

data class Semester (
    val department: String,
    val number: Int,
    val subjectList: List<Subject>
    )