package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Resource

sealed class RecentAction: Action {
    object GetRecentPapers: RecentAction()
    class StoreRecentPaper(val paper: Paper): RecentAction()
    class StoreRecentResource(val resource: Resource): RecentAction()
}