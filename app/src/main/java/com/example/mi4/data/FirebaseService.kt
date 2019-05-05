package com.example.mi4.data

import com.example.mi4.data.db.entity.Item
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Deferred


interface FirebaseService {
    val USER_ID: String
    fun getItems(): Deferred<List<Item>>

    companion object{
        operator fun invoke(): FirebaseService
    }
}