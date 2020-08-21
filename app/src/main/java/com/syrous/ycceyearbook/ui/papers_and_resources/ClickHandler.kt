package com.syrous.ycceyearbook.ui.papers_and_resources

import com.syrous.ycceyearbook.model.Paper

interface ClickHandler {
    fun onClick(paper: Paper)
}