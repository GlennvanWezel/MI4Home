package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "rooms")
data class Room (var amountOfItems: Int = 0,
                 var name: String = "Default",
                 var value: Double = 0.00){
    override fun toString(): String {
        return "N: $name|#Items:$amountOfItems|$$value"
    }

    @PrimaryKey(autoGenerate = false)
    var id: String  = ""

}