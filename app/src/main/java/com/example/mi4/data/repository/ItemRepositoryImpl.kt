package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.ItemDao
import com.example.mi4.data.db.entity.User
import com.example.mi4.data.network.ItemNetworkDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ItemRepositoryImpl : ItemRepository {

    fun addItem(item: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayUnion(item))
    }

    override suspend fun getCurrentItems(): List<Item> {
        val user = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(
                FirebaseAuth
                    .getInstance()
                    .currentUser!!
                    .uid
            )
            .get()
            .await()
            .toObject(User::class.java)
        return user!!.items
    }

    companion object{
        @Volatile private var instance: ItemRepositoryImpl? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: ItemRepositoryImpl().also { instance = it }
            }
    }
}