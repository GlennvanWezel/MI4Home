package com.example.mi4.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class Type (var amountOfItems: Int = 0,
                 var name: String = "Default",
                 var value: Double = 0.00){
    override fun toString(): String {
        return "$name - #Items: $amountOfItems - $$value"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long  = 0

}