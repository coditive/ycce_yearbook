package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action

data class SentryAction (
        val error: Throwable
        ): Action