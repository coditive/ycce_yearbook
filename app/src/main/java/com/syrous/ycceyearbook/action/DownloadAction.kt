package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action
import com.syrous.ycceyearbook.model.Paper

sealed class DownloadAction: Action {
    data class DownloadPaper(val paper: Paper): DownloadAction()
}