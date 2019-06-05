package com.example.mi4.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "items")
data class Item (var naam: String = "Default",
                 var kamer: String = "Default",
                 var type: String ="Default",
                 var waarde: Double = 0.00){
    override fun toString(): String {
        return "$naam - kamer: $kamer - categorie: $type - worth: $waarde"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Long  = 0

}