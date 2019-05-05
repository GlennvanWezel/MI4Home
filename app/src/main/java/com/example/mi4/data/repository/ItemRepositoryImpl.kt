package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.ItemDao
import com.example.mi4.data.network.ItemNetworkDataSource

class ItemRepositoryImpl (
    private val itemDao: ItemDao,
    private val itemNetworkDataSource: ItemNetworkDataSource
    ): ItemRepository {

    init {
        itemNetworkDataSource.toString()
    }

    override suspend fun getCurrentItems(userID: String): LiveData<List<Item>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}