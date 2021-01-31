package com.syrous.ycceyearbook.action

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.syrous.ycceyearbook.model.User


sealed class AccountAction(
    override val eventMethod: TelemetryEventMethod,
    override val eventObject: TelemetryEventObject,
    override val value: String? = null,
    override val extras: Map<String, Any>? = null
) : TelemetryAction {
    object AutomaticLogin : AccountAction(TelemetryEventMethod.automatic_log_in, TelemetryEventObject.account_store)
    object GoogleLogin: AccountAction(TelemetryEventMethod.log_in, TelemetryEventObject.activity)
    object InitiateLogin: AccountAction(TelemetryEventMethod.log_in, TelemetryEventObject.activity)
    data class FirebaseLogin(val account: GoogleSignInAccount): AccountAction(TelemetryEventMethod.log_in, TelemetryEventObject.account_store)
    data class LoggedIn(val user: User): AccountAction(TelemetryEventMethod.logged_in,
        TelemetryEventObject.account_store)
    object Reset: AccountAction(TelemetryEventMethod.reset, TelemetryEventObject.account_store)
}