package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class Type (var amountOfItems: Int = 0,
                 var name: String = "Default",
                 var value: Double = 0.00){
    override fun toString(): String {
        return "$id - Type: $name - Amount Of Items: $amountOfItems - Value: $value"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long  = 0

}