package uk.co.vanwezel.mi4.data.repository

import uk.co.vanwezel.mi4.data.model.Room

interface RoomRepository {
    //suspend enables you to call a function from a corouting (Asynchronously)
    suspend fun getCurrentRooms()
    suspend fun deleteRoom(room: Room)
    suspend fun addRoom(room: Room)
   // suspend fun updateRoom(oldRoom: Room,newRoom: Room)
}