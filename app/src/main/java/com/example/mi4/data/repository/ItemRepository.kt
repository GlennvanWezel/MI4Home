package com.example.mi4.data.repository

import android.content.ClipData
import androidx.lifecycle.LiveData
import com.example.mi4.data.db.entity.Item

interface ItemRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentItems(userID: String): LiveData<List<Item>>
}