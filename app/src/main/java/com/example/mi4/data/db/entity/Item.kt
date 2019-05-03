package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "items")
data class Item (var naam: String, var kamer: String, var type: String, var waarde: Double){
    override fun toString(): String {
        return "$naam - kamer: $kamer - categorie: $type - worth: $waarde"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long  = 0

}