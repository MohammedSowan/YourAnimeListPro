package com.example.youranimelist2pro.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.youranimelist.R
import com.example.youranimelist.databinding.FragmentLoginBinding
import com.example.youranimelist2pro.MainFragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: FragmentLoginBinding
    private var KEY = "key"
    private val NAME = "name"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val sharedPreferences = requireContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
        val isLoggedIn: Boolean = sharedPreferences.getBoolean(KEY, false)

        binding.gSignInBtn.setOnClickListener {
            signInGoogle()

        }

        if (isLoggedIn) {
            MoveActivites()
        }



        return binding.root
    }

    private fun signInGoogle() {

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)

        LoginStatusUpdate()
        MoveActivites()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            } else {

            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                MoveActivites()
            } else {
            }
        }
    }

    private fun LoginStatusUpdate() {
        val sharedPreferences = requireContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putBoolean(KEY, true)
        editor.apply()
    }

    private fun MoveActivites() {
            val i = Intent(activity, MainFragmentActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
    }


}
