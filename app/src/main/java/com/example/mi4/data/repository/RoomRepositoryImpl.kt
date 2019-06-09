package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Room
import com.example.mi4.data.db.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class RoomRepositoryImpl : RoomRepository {
    override suspend fun updateRoom(oldRoom: Room, newRoom: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayRemove(oldRoom))
            .await()
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayUnion(newRoom))
            .await()
    }

    override suspend fun deleteRoom(room: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayRemove(room))
            .await()
    }

    override suspend fun addRoom(room: Room){

    }

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

    companion object {
        @Volatile
        private var instance: RoomRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: RoomRepositoryImpl().also { instance = it }
            }
    }
}