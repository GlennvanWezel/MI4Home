package com.example.mi4.ui.items.manage

import android.provider.Settings
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.db.entity.Room
import com.example.mi4.data.db.entity.Type
import com.example.mi4.data.repository.ItemRepositoryImpl
import com.example.mi4.data.repository.RoomRepositoryImpl
import com.example.mi4.data.repository.TypeRepositoryImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddItemViewModel : ViewModel() {

    private var itemsRepo: ItemRepositoryImpl = ItemRepositoryImpl()
    private var roomsRepo: RoomRepositoryImpl = RoomRepositoryImpl()
    private var typesRepo: TypeRepositoryImpl = TypeRepositoryImpl()
    var roomList = MutableLiveData<List<Room>>()
    var typeList = MutableLiveData<List<Type>>()
    var itemList = MutableLiveData<List<Item>>()
    var rooms: LiveData<List<Room>>
    var types: LiveData<List<Type>>
    var items: LiveData<List<Item>>

    init {
        items = itemsRepo.items
        rooms = roomsRepo.rooms
        types = typesRepo.types
        itemsRepo.items.observeForever {
            itemList.value = it
            items = itemList
        }
        typesRepo.types.observeForever {
            typeList.value = it
            types = typeList
        }
        roomsRepo.rooms.observeForever {
            roomList.value = it
            rooms = roomList
        }
    }


    fun addItem(item: Item) {
        itemsRepo.addItem(item)
    }

    fun addItem(naam: String, room: String, type: String, waarde: Double) {
        val item = Item(naam, room, type, waarde)
        addItem(item)
    }
}
