package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Item

interface ItemRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentItems(): List<Item>
    suspend fun deleteItem(item: Item)
    fun addItem(item: Item)
    suspend fun updateItem(olditem: Item,newitem: Item)
}