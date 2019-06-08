package com.example.mi4.ui.items.manage

import android.provider.Settings
import android.util.Log
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

    private var itemsRepo : ItemRepositoryImpl = ItemRepositoryImpl()
    private var roomsRepo : RoomRepositoryImpl = RoomRepositoryImpl()
    private var typesRepo : TypeRepositoryImpl = TypeRepositoryImpl()
    var roomList = ArrayList<Room>()
    var typeList = ArrayList<Type>()
    var itemList = ArrayList<Item>()
    var rooms = MutableLiveData<List<Room>>()
    var types = MutableLiveData<List<Type>>()
    var items = MutableLiveData<List<Item>>()
    init {
        GlobalScope.launch {
            val currentrooms = roomsRepo.getCurrentRooms()
            val currentitems = itemsRepo.getCurrentItems()
            val currenttypes = typesRepo.getCurrentTypes()

            roomList.addAll(currentrooms)
            typeList.addAll(currenttypes)
            itemList.addAll(currentitems)

            rooms.postValue(currentrooms)
            types.postValue(currenttypes)
            items.postValue(currentitems)

        }
    }


    fun addItem(item: Item){
        itemList.add(item)
        itemsRepo.addItem(item)

        items.postValue(itemList)
    }

    fun addItem(naam: String, room: String, type: String, waarde: Double){
        val item = Item(naam,room,type,waarde)
        itemList.add(item)
        itemsRepo.addItem(item)

        items.postValue(itemList)
    }
}
