package com.syrous.ycceyearbook.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentSplashBinding
import com.syrous.ycceyearbook.model.Result.Success
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.ui.home.ActivityHome
import com.syrous.ycceyearbook.util.USER_COLLECTION
import com.syrous.ycceyearbook.util.user.UserDataRepository

class FragmentSplash: Fragment() {

    private lateinit var userDataRepository: UserDataRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSplashBinding.inflate(layoutInflater, container, false).root


    override fun onAttach(context: Context) {
        super.onAttach(context)
       userDataRepository =  (requireActivity().application as YearBookApplication).appComponent.userRepository()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val user = userDataRepository.getLoggedInUser()
            if(user is Success) {
                user.data.timestamp = FieldValue.serverTimestamp()
                syncDataWithServer(user.data)
            } else {
                findNavController().navigate(FragmentSplashDirections.actionFragmentSplashToFragmentLogin())
            }

    }

    private fun syncDataWithServer(user: User) {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection(USER_COLLECTION).document(user.googleid)
        query.set(user).addOnCompleteListener(OnCompleteListener {
            if(it.isSuccessful) {
                startActivity(Intent(requireActivity(), ActivityHome::class.java))
            } else {
                TODO("Error Networking is not there")
            }
        })
    }
}