package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoomRepositoryImpl : RoomRepository {
    private var roomsList = MutableLiveData<List<Room>>()

    var rooms: LiveData<List<Room>>
        get() {
            return roomsList
        }

    init {
        rooms = roomsList
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentRooms()
                }
            } else if (it.currentUser == null) {
                roomsList = MutableLiveData()
                rooms = roomsList
            }
        }
    }

    //region CRUD METHODS
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
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayUnion(room))
            .await()
    }

    override suspend fun getCurrentRooms() {
        roomsList.postValue(FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get(Source.CACHE)
            .await()
            .toObject(User::class.java)!!
            .rooms)
        rooms = roomsList
    }
//endregion

    companion object {
        @Volatile
        private var instance: RoomRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: RoomRepositoryImpl().also { instance = it }
            }
    }
}