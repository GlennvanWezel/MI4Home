package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoomRepositoryImpl : RoomRepository {
    private var roomsList = mutableListOf<Room>()

    var rooms = MutableLiveData<List<Room>>()

    fun getRooms(): LiveData<List<Room>> {
        return rooms
    }

    init {
        rooms.postValue(roomsList)
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentRooms()
                }
            } else if (it.currentUser == null) {
                roomsList = mutableListOf<Room>()
                rooms.postValue(roomsList)
            }
        }
    }

    //region CRUD METHODS
     suspend fun updateRoom(room: Room) {
        roomsList[roomsList.indexOf(room)] = room
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms",roomsList)
            .await()
        rooms.postValue(roomsList)

    }

    override suspend fun deleteRoom(room: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayRemove(room))
            .await()
        roomsList.remove(room)
        rooms.postValue(roomsList)
    }

    override suspend fun addRoom(room: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayUnion(room))
            .await()
        roomsList.add(room)
        rooms.postValue(roomsList)
    }

    override suspend fun getCurrentRooms() {
        roomsList = (
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .get()
                    .await()
                    .toObject(User::class.java)!!
                    .rooms
                ).toMutableList()
        rooms.postValue(roomsList)
    }
//endregion


    fun getRoom(name: String): Room? {
        return roomsList.find {
            it.name == name
        }
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