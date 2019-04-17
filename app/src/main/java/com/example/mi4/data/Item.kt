package com.example.mi4.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Item (var naam: String, var kamer: String, var type: String, var waarde: Double){
    override fun toString(): String {
        return "$naam - kamer: $kamer - categorie: $type - worth: $waarde"
    }
}