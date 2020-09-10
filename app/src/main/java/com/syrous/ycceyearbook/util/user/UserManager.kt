package com.syrous.ycceyearbook.util.user

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.syrous.ycceyearbook.model.Result
import com.syrous.ycceyearbook.model.Result.Error
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject


/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 */

class UserManager @Inject constructor (
     private val googleSignInClient: GoogleSignInClient,
     private val auth: FirebaseAuth
) {

    suspend fun loginUser(account: GoogleSignInAccount): Result<User> {
        firebaseAuthWithGoogle(account.idToken!!)
        delay(3000)
        return if (auth.currentUser != null) {
                if (account.id != null) {
                    val user = User(
                        account.id!!,
                        auth.currentUser!!.uid,
                        account.displayName,
                        account.email,
                        null,
                        account.photoUrl.toString(),
                        FieldValue.serverTimestamp()
                    )
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
        delay(3000)
        //Google SignOut
        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                result = Success(true)
            }
        }
        return result
    }

    private suspend fun firebaseAuthWithGoogle (id: String) {
        // Getting Sign In Credential from google sign in api
        val credential = GoogleAuthProvider.getCredential(id, null)
        // Sign User into firebase
        delay(2000)
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

}