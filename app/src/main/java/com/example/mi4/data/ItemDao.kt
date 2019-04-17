package com.example.mi4.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

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