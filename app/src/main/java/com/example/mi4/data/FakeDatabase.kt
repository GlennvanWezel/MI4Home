package com.example.mi4.data

class FakeDatabase private constructor() {

    var itemDao = itemDao()

    companion object{
        @Volatile private var instance: FakeDatabase? = null

        fun getInstance() =
                instance ?: synchronized(this){
                    instance ?: FakeDatabase().also { instance = it }
                }
    }
}