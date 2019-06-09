package com.example.mi4.data.network

import androidx.lifecycle.LiveData
import com.example.mi4.data.db.entity.Item

interface ItemNetworkDataSource {
    val downloadedItems: LiveData<List<Item>>

//    suspend fun fetchItems(userID: String)
}