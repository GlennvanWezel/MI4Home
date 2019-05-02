package com.example.mi4.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.db.entity.Item

class ItemDao {
    private val itemlist = mutableListOf<Item>()
    private val items = MutableLiveData<List<Item>>()
    init {
        items.value = itemlist
    }

    fun addItem(item: Item){
        itemlist.add(item)
        items.value = itemlist
    }

    fun getItems() = items as LiveData<List<Item>>
    
}