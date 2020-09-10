package com.syrous.ycceyearbook.model

import com.google.firebase.firestore.FieldValue


data class User (
    val googleid: String,
    val authid: String,
    val name: String?,
    val email: String?,
    val ntToken: String? = null,
    val profilePhotoUrl: String?,
    var timestamp: FieldValue?
)