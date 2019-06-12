package com.example.mi4.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("items")
    var items: List<Item> = listOf(),
    @SerializedName("rooms")
    var rooms: List<Room> = listOf(),
    @SerializedName("types")
    var types: List<Type> = listOf()
){

    override fun toString(): String {
        return "id: $id | Items: $items | rooms: $rooms | types: $types"
    }

    @PrimaryKey(autoGenerate = false)
    @SerializedName("key")
    var id: String  = ""
}