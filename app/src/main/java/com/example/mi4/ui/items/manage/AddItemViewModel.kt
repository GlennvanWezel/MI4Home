package com.example.mi4.ui.items.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        rooms = roomsRepo.rooms // TODO: Change to .getrooms()
        types = typesRepo.types // TODO: change to .gettypes()
        itemsRepo.getitems().observeForever {
            items = itemsRepo.getitems()
        }
        typesRepo.types.observeForever {
            types = typesRepo.types // TODO: change to .gettypes()
        }
        roomsRepo.rooms.observeForever {
            rooms = roomsRepo.rooms // TODO: Change to .getrooms()
        }
    }


    fun addItem(item: Item,room: Room, type: Type) {
        val oldroom = room.copy()
        val oldtype = type.copy()

        room.amountOfItems += 1
        type.amountOfItems += 1

        room.value += item.waarde
        type.value += item.waarde

        GlobalScope.launch(Dispatchers.IO){
            roomsRepo.updateRoom(oldroom,room)
            typesRepo.updateType(oldtype,type)
        }
        itemsRepo.addItem(item)

    }


    fun addRoom(room: Room){
        GlobalScope.launch(Dispatchers.IO){
            roomsRepo.addRoom(room)
        }
    }

    fun addType(type: Type){
        GlobalScope.launch(Dispatchers.IO){
            typesRepo.addType(type)
        }
    }
}
