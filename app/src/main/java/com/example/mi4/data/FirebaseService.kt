package com.example.mi4.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Deferred
interface FirebaseService {
    val mAuth : FirebaseAuth

    fun login(email: String, password: String): Deferred<{
        mAuth.signInWithEmailAndPassword(email,password)
    }
}