package com.syrous.ycceyearbook.ui.recent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.syrous.ycceyearbook.data.Repository
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Success
import java.io.File
import javax.inject.Inject

class RecentVM @Inject constructor(private val repository: Repository): ViewModel() {

    val listOfPapers = repository.listOfRecentPapers.map {
        filterPaper(it)
    }

    private fun filterPaper(paperResult: Result<List<Paper>>): List<Paper> {
        val result = emptyList<Paper>()
       return if(paperResult is Success){
            paperResult.data
        } else {
           result
       }
    }

     fun getFileFromStorage(context: Context, paper: Paper): File {
        val path = context.getExternalFilesDir("papers")
        return File(path, "paper${paper.id}.pdf")
    }

}