package com.example.mi4.data.repository

import com.example.mi4.data.db.entity.Room

interface RoomRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentRooms(): List<Room>
}