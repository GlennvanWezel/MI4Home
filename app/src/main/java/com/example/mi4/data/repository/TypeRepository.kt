package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Type

interface TypeRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentTypes(): List<Type>
}