package com.example.mi4.data.repository

import com.example.mi4.data.model.Type

interface TypeRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentTypes()
    suspend fun deleteType(type: Type)
    suspend fun addType(type:Type)
   // suspend fun updateType(oldType: Type,newType: Type)
}