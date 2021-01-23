package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action

sealed class AccountAction : Action {
    data class OauthRedirect(val url: String): AccountAction()
}