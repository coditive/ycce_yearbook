package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action
import com.syrous.ycceyearbook.model.Paper

sealed class RecentAction: Action {
    object GetRecentPapers: RecentAction()
    class StoreRecentPaper(val paper: Paper): RecentAction()
}