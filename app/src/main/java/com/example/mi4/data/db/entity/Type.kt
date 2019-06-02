package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "types")
data class Type (var amountOfItems: Int, var name: String, var value: Double){
    override fun toString(): String {
        return "$id - Type: $name - Amount Of Items: $amountOfItems - Value: $value"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long  = 0

}