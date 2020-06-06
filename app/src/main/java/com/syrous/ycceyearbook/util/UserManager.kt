package com.syrous.ycceyearbook.util

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.syrous.ycceyearbook.data.local.UserDao
import com.syrous.ycceyearbook.data.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 */
class UserManager @Inject constructor (
    private val storage: UserDao,
    private val account: GoogleSignInAccount,
    private val googleSignInClient: GoogleSignInClient,
    private val auth: FirebaseAuth,
    private val userComponentFactory: UserComponent.Factory,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    var userDataRepository: UserDataRepository? = null

    var userComponent: UserComponent? = null
        private set

    fun isUserLoggedIn() = userComponent != null

    fun isUserRegistered() {
        TODO()
    }

    fun getCurrentUser() {
        TODO()
    }

    suspend fun loginUser(): Result<String> {

        account.id?.let {
            firebaseAuthWithGoogle(it)
        }

        TODO("Implement login through firebase and google sign in")
    }

    fun logout() {
        //Firebase signOut
        auth.signOut()

        //Google SignOut
        googleSignInClient.signOut().addOnCompleteListener {
            TODO("Inform UI About logout")
        }
    }

    private fun firebaseAuthWithGoogle (id: String) {
        //Getting Sign In Credential from google sign in api
        val credential = GoogleAuthProvider.getCredential(id, null)

        // Sign User into firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    TODO("Return User Details to UI")
                } else {
                    TODO("Return Error details to UI")
                }
            }
    }

    private fun userJustLoggedIn() {
        //This keeps data till the user is logged in.
        userComponent = userComponentFactory.create()
    }
}