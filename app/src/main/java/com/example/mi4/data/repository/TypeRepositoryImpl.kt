package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Type
import com.example.mi4.data.db.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class TypeRepositoryImpl : TypeRepository {
    override suspend fun getCurrentTypes(): List<Type> {
        return FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get(Source.CACHE)
            .await()
            .toObject(User::class.java)!!
            .types
    }

    companion object {
        @Volatile
        private var instance: TypeRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: TypeRepositoryImpl().also { instance = it }
            }
    }
}