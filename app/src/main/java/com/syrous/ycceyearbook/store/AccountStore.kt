package com.syrous.ycceyearbook.store

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.syrous.ycceyearbook.action.AccountAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class AccountStore @Inject constructor(
    private val context: Application,
    private val dispatcher: Dispatcher,
    private val googleSignInClient: GoogleSignInClient,
    private val auth: FirebaseAuth,
    private val coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    sealed class State {
        object Login: State()
        object GoogleLogin: State()
        object Reset: State()
        data class LoggedInUserDetails(val user: User): State()
    }

    enum class SyncState {
        Syncing, NotSyncing
    }

    private val _accountState = MutableSharedFlow<State>()
    val accountState: SharedFlow<State> = _accountState

    private val _syncState = MutableStateFlow(SyncState.NotSyncing)
    val syncState: StateFlow<SyncState> = _syncState

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<AccountAction>()
                .collect { action ->
                    when(action){
                        is AccountAction.AutomaticLogin -> automaticLogin()
                        is AccountAction.FirebaseLogin -> loginIntoFirebase(account = action.account)
                        is AccountAction.LoggedIn -> _accountState
                            .emit(State.LoggedInUserDetails(action.user))
                        is AccountAction.Reset -> signOut()
                        is AccountAction.GoogleLogin -> _accountState.emit(State.Login)
                        is AccountAction.InitiateLogin -> _accountState.emit(State.GoogleLogin)
                    }
                }
        }
    }

    private fun automaticLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        val auth = FirebaseAuth.getInstance()
        if (account != null){
            val user = User(account.id!!, auth.currentUser!!.uid, account.displayName,
                account.email,null, account.photoUrl.toString(),
                FieldValue.serverTimestamp())
            dispatcher.dispatch(AccountAction.LoggedIn(user))
        } else {
            dispatcher.dispatch(AccountAction.GoogleLogin)
        }
    }

    private fun loginIntoFirebase(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
                task ->
            if(task.isSuccessful) {
                Timber.d("Account Auth success now emitting user details")
                val currentUser = auth.currentUser!!
                val user = User(account.id!!, currentUser.uid, account.displayName, account.email,null,
                    account.photoUrl.toString(), FieldValue.serverTimestamp())
                dispatcher.dispatch(AccountAction.LoggedIn(user))
            } else {
                dispatcher.dispatch(SentryAction(task.exception!!))
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
    }
}