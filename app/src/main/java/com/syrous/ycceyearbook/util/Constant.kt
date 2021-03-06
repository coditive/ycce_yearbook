package com.syrous.ycceyearbook.util

import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.model.Department
import com.syrous.ycceyearbook.ui.home.OtherFeature

const val BASE_URL = "https://us-central1-ycce-yearbook.cloudfunctions.net/app/api/"
const val YEARBOOK_DB_NAME = "yearbook_database.db"

const val SUBJECT_OBJECT = "subject_object"
const val PAPER_OBJECT = "paper_object"
const val PAPER_DOWNLOADER = "paper_downloader"
const val PDF_FILE_OBJECT = "pdfRef"

//USER FIELDS
const val USER_SP_KEY = "user_sp_key"
const val USER_EMAIL = "user_email"
const val USER_GOOGLE_ID = "user_google_id"
const val USER_AUTH_ID = "user_auth_id"
const val USER_NAME = "user_name"
const val USER_PHOTO_URL = "user_photo_url"
const val USER_NOTIFICATION_TOKEN = "user_notification_token"
const val USER_TIMESTAMP = "user_timestamp"

// Notification Service
const val USER_COLLECTION = "users"
const val UPDATE_TIMESTAMP = "update_timestamp"

object Constant {

    object RequestCode {
        const val noResult = 0
    }

    object Department {
        const val DEPARTMENT_NAME = "department_name"
    }

    object Indicator {
        const val loading = "loading_spiral.json"
    }

   const val Resource = "resource_object"
}



// Global Functions

fun getOtherFeatureList(): List<OtherFeature> {
    val otherFeature = mutableListOf<OtherFeature>()
    otherFeature.add(OtherFeature("YCCE", R.drawable.home, "https://www.ycce.edu"))
    otherFeature.add(OtherFeature("Moodle", R.drawable.moodle_icon, "https://ycce.in/"))
    otherFeature.add(OtherFeature("Upload Paper", R.drawable.upload_icon, "1231"))
    otherFeature.add(OtherFeature("Fees Payment", R.drawable.pay_icon, "https://www.ycce.edu"))
    return otherFeature
}

fun getDepartmentList(): List<Department> {
    val department = mutableListOf<Department>()
    department.add(Department(1,"ct", R.drawable.ct_art, R.drawable.ct_art_large, R.color.ct_back, R.color.ct_border,1))
    department.add(Department(2,"it", R.drawable.it_art, R.drawable.it_art_large, R.color.it_back, R.color.it_border,2))
    department.add(Department(3,"me", R.drawable.me_art, R.drawable.me_art_large, R.color.me_back, R.color.me_border,3))
    department.add(Department(4,"cv", R.drawable.cv_art, R.drawable.cv_art_large, R.color.cv_back, R.color.cv_border,4))
    department.add(Department(5,"ee", R.drawable.ee_art, R.drawable.ee_art_large, R.color.ee_back, R.color.ee_border,5))
    department.add(Department(6,"el", R.drawable.el_art, R.drawable.el_art_large, R.color.el_back, R.color.el_border,6))
    department.add(Department(7,"etc", R.drawable.etc_art, R.drawable.etc_art_large, R.color.etc_back, R.color.etc_border,7))
    department.add(Department(8,"fy", R.drawable.fy_art, R.drawable.fy_art_large, R.color.fy_back, R.color.fy_border,8))
    return department
}