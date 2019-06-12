package com.example.mi4.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class Room (var amountOfItems: Int = 0,
                 var name: String = "Default",
                 var value: Double = 0.00){
    override fun toString(): String {
        return "$name - #Items:$amountOfItems - $$value"
    }

    @PrimaryKey(autoGenerate = false)
    var id: String  = ""

}