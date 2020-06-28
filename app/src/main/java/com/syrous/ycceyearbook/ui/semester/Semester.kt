package com.syrous.ycceyearbook.ui.semester

import com.syrous.ycceyearbook.data.model.Subject

data class Semester (
    val name: String,
    val subjectList: List<Subject>
)