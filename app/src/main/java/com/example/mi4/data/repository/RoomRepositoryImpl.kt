package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Room
import com.example.mi4.data.db.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class RoomRepositoryImpl : RoomRepository {
    override suspend fun getCurrentRooms(): List<Room> {
        return FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get(Source.CACHE)
            .await()
            .toObject(User::class.java)!!
            .rooms
    }
}