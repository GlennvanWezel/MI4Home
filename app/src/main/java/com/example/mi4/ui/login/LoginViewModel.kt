package com.example.mi4.ui.login

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    // TODO: Implement the ViewModel
    private lateinit var  auth : FirebaseAuth
    fun login(username: String, password: String) {

    }
}
