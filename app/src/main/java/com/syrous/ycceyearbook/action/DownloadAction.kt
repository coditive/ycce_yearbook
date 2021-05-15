package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource

sealed class DownloadAction: Action {
    data class DownloadPaper(val paper: Paper): DownloadAction()
    data class DownloadResource(val resource: Resource): DownloadAction()
}