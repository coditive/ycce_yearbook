package com.syrous.ycceyearbook.store

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.syrous.ycceyearbook.action.AccountAction
import com.syrous.ycceyearbook.action.SentryAction
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Success
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
    private val storage: UserSharedPrefStorage,
    private val googleSignInClient: GoogleSignInClient,
    private val auth: FirebaseAuth,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    sealed class State {
        object Welcome: State()
        object GoogleLogin: State()
        object Reset: State()
        data class LoggedInUserDetails(val user: User): State()
    }

    enum class SyncState {
        Syncing, NotSyncing
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _accountState = MutableSharedFlow<State>()
    val accountState: SharedFlow<State> = _accountState

    private val _syncState = MutableStateFlow(SyncState.NotSyncing)
    val syncState: StateFlow<SyncState> = _syncState

    init {
        Timber.d("CoroutineContext: $coroutineContext")
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .filterIsInstance<AccountAction>()
                .collect { action ->
                    when(action){
                        is AccountAction.AutomaticLogin -> automaticLogin()
                        is AccountAction.FirebaseLogin -> loginIntoFirebase(account = action.account)
                        is AccountAction.LoggedIn -> _accountState
                            .emit(State.LoggedInUserDetails(action.user))
                        is AccountAction.Reset -> signOut()
                        is AccountAction.GoogleLogin -> _accountState.emit(State.Welcome)
                        is AccountAction.InitiateLogin -> _accountState.emit(State.GoogleLogin)
                    }
                }
        }
    }

    private fun automaticLogin() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null){
           val result = storage.getLoggedInUser()
            if(result is Success) {
                dispatcher.dispatch(AccountAction.LoggedIn(result.data))
            }else if(result is Result.Error) {
                Timber.e(result.exception)
                dispatcher.dispatch(SentryAction(result.exception))
            }

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
                storage.saveAccount(user)
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