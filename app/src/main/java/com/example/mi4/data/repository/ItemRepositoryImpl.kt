package com.example.mi4.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.ItemDao
import com.example.mi4.data.db.entity.User
import com.example.mi4.data.network.ItemNetworkDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirestoreRegistrar
import com.google.firebase.firestore.Source
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ItemRepositoryImpl : ItemRepository {

    private var firestoreDataChanged = true

    init {
        //datachange listeners
        GlobalScope.launch {
            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .addSnapshotListener{snapchat, e ->
                    firestoreDataChanged = !firestoreDataChanged
                }
            getCurrentItems()
        }
    }


    //region CRUD METHODS
    override suspend fun deleteItem(item: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayRemove(item))
            .await()
    }

    override suspend fun updateItem(olditem: Item, newitem: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayRemove(olditem))
            .await()
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayUnion(newitem))
            .await()
    }






    fun addItem(item: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayUnion(item))
    }

    override suspend fun getCurrentItems(): List<Item> {
        if(firestoreDataChanged){
            val user = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(
                    FirebaseAuth
                        .getInstance()
                        .currentUser!!
                        .uid
                )
                .get(Source.SERVER)
                .await()
                .toObject(User::class.java)
            Log.d("Fetching Data From Firestore", "GOT FROM INTERNET: $user")
            firestoreDataChanged = !firestoreDataChanged
            return user!!.items
        }
        else{
            val user = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(
                    FirebaseAuth
                        .getInstance()
                        .currentUser!!
                        .uid
                )
                .get(Source.CACHE)
                .await()
                .toObject(User::class.java)
            Log.d("Fetching Data From Firestore", "GOT FROM CACHE: $user")
            return user!!.items
        }
    }

    //endregion

    companion object{
        @Volatile private var instance: ItemRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this){
                instance ?: ItemRepositoryImpl().also { instance = it }
            }
    }
}