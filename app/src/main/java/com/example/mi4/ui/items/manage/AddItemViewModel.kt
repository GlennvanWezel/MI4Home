package com.example.mi4.ui.items.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mi4.data.model.Item
import com.example.mi4.data.model.Room
import com.example.mi4.data.model.Type
import com.example.mi4.data.repository.ItemRepositoryImpl
import com.example.mi4.data.repository.RoomRepositoryImpl
import com.example.mi4.data.repository.TypeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddItemViewModel : ViewModel() {

    private var itemsRepo: ItemRepositoryImpl = ItemRepositoryImpl()
    private var roomsRepo: RoomRepositoryImpl = RoomRepositoryImpl()
    private var typesRepo: TypeRepositoryImpl = TypeRepositoryImpl()
    var rooms: LiveData<List<Room>>
    var types: LiveData<List<Type>>
    var items: LiveData<List<Item>>

    init {
        items = itemsRepo.getitems()
        rooms = roomsRepo.getRooms() // TODO: Change to .getrooms()
        types = typesRepo.getTypes() // TODO: change to .gettypes()
        itemsRepo.getitems().observeForever {
            items = itemsRepo.getitems()
        }
        typesRepo.types.observeForever {
            types = typesRepo.getTypes() // TODO: change to .gettypes()
        }
        roomsRepo.rooms.observeForever {
            rooms = roomsRepo.getRooms() // TODO: Change to .getrooms()
        }
    }


    fun addItem(item: Item,room: Room, type: Type) {
        room.amountOfItems += 1
        type.amountOfItems += 1

        room.value += item.waarde
        type.value += item.waarde

        GlobalScope.launch(Dispatchers.IO){
            roomsRepo.updateRoom(room)
            typesRepo.updateType(type)
        }
        itemsRepo.addItem(item)

    }


    fun addRoom(room: Room){
        GlobalScope.launch(Dispatchers.IO){
            roomsRepo.addRoom(room)
        }
    }

    fun addRoom(name: String){
        val room = Room(0,name,0.00)
        GlobalScope.launch(Dispatchers.IO){
            roomsRepo.addRoom(room)
        }
    }

    fun addType(type: Type){
        GlobalScope.launch(Dispatchers.IO){
            typesRepo.addType(type)
        }
    }
    fun addType(name: String){
        val type = Type(0,name,0.00)
        GlobalScope.launch(Dispatchers.IO){
            typesRepo.addType(type)
        }
    }

    fun deleteType(typeToDelete: Type, moveAttachedItems: Boolean, typeToMoveTo: Type?){
        if(!moveAttachedItems){
            //get all the items associated with this type
            var items = itemsRepo.getAllItemsByType(typeToDelete.name)
            //delete all the items collected above
            GlobalScope.launch {
                items.forEach {
                    itemsRepo.deleteItem(it)
                }
                //delete the type
                typesRepo.deleteType(typeToDelete)
            }
        }
        
    }

    fun deleteRoom(roomToDelete: Room, moveAttachedItems : Boolean, roomToMoveTo: Room?){
        if(!moveAttachedItems){
            //get all the items associated with this room
            var items = itemsRepo.getAllItemsByRoom(roomToDelete.name)
            //delete all the items collected above
            GlobalScope.launch {
                items.forEach {
                    itemsRepo.deleteItem(it)
                }
                //delete the room
                roomsRepo.deleteRoom(roomToDelete)
            }

        }
    }

    companion object {
        @Volatile
        private var instance: AddItemViewModel? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: AddItemViewModel().also { instance = it }
            }
    }
}
