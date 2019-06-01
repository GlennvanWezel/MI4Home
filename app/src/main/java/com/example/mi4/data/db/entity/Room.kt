package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "rooms")
data class Room (var amountOfItems: Int, var name: String, var value: Double){
    override fun toString(): String {
        return "$id - Room: $name - Amount Of Items: $amountOfItems - Value: $value"
    }

    @PrimaryKey(autoGenerate = false)
    var id: String  = ""

}