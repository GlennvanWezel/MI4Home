package com.example.mi4.data

import com.example.mi4.data.db.entity.Item

class ItemRepository private constructor(private val itemDao: ItemDao){

    fun addItem(item: Item){
        itemDao.addItem(item)
    }

    fun getItems() = itemDao.getItems()


    companion object{
        @Volatile private var instance: ItemRepository? = null

        fun getInstance(itemDao: ItemDao) =
            instance ?: synchronized(this){
                instance ?: ItemRepository(itemDao).also { instance = it }
            }
    }
}
