package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.ItemDao
import com.example.mi4.data.network.ItemNetworkDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class ItemRepositoryImpl(
    private val itemDao: ItemDao,
    private val itemNetworkDataSource: ItemNetworkDataSource
) : ItemRepository {

    init {
        itemNetworkDataSource.toString()
    }

    override suspend fun getCurrentItems(userID: String): LiveData<List<Item>> {
//        GlobalScope.launch(Dispatchers.IO) {
////            var user = FirebaseFirestore
////                .getInstance()
////                .collection("users")
////                .document(
////                    FirebaseAuth
////                        .getInstance()
////                        .currentUser!!
////                        .uid
////                )
////                .get()
////        }
        return MutableLiveData<List<Item>>()
    }
}