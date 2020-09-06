package com.syrous.ycceyearbook.model


data class User (
    val googleid: String,
    val authid: String,
    val name: String?,
    val email: String?,
    val profilePhotoUrl: String?
)