package com.syrous.ycceyearbook.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class SemSubModel

data class Semester (
    val department: String,
    val sem: Int
): SemSubModel()
