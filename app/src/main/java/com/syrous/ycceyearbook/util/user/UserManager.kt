package com.syrous.ycceyearbook.util.user

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.syrous.ycceyearbook.data.UserSharedPrefStorage
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 */
@Singleton
class UserManager @Inject constructor (
     private val googleSignInClient: GoogleSignInClient,
     private val auth: FirebaseAuth,
     private val userComponentFactory: UserComponent.Factory,
     private val storage: UserSharedPrefStorage
) {
    var userComponent: UserComponent? = null
        private set

    fun isUserLoggedIn() = userComponent != null

    fun getCurrentUser(): Result<User> {
        return storage.getLoggedInUser()
    }

    suspend fun loginUser(account: GoogleSignInAccount): Result<User> {
        firebaseAuthWithGoogle(account.idToken!!)
        delay(1200)
        return if (auth.currentUser != null) {
                if (account.id != null) {
                    val user = User(
                        account.id!!,
                        account.displayName,
                        account.email,
                        account.photoUrl.toString()
                    )
                    userJustLoggedIn()
                    storage.saveAccount(user)
                    Success(user)
                } else {
                    Timber.e(Exception("Invalid Id Params"))
                    Error(Exception("Invalid Id Params"))
                }
            } else {
                Timber.e(Exception("Firebase Error, Account was unable to Login in!!"))
                Error(Exception("Firebase Error, Account was unable to Login in!!"))
            }
    }

    suspend fun logout(): Result<Boolean> {
        //Firebase signOut
        auth.signOut()

        var result: Result<Boolean> = Error(Exception("Function not Executed!!"))

        delay(1000)

        //Google SignOut
        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                result = Success(true)
            }
        }

        userComponent = null
        return result
    }

    private suspend fun firebaseAuthWithGoogle (id: String) {
        // Getting Sign In Credential from google sign in api
        val credential = GoogleAuthProvider.getCredential(id, null)
        // Sign User into firebase
        delay(1000)
         auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("User name : ${user?.displayName}")
                } else {
                    Timber.e(it.exception)
                }
            }

    }

    private fun userJustLoggedIn() {
        //This keeps data till the user is logged in.
        userComponent = userComponentFactory.create()
    }
}