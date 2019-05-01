package com.example.mi4.ui.login

import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private lateinit var  auth : FirebaseAuth
    fun login(username: String, password: String): AuthResult?{
        auth = FirebaseAuth.getInstance()
        var logintask = auth.signInWithEmailAndPassword(username, password)
        return logintask.result
    }
}
