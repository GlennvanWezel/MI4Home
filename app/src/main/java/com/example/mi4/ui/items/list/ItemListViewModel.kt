package com.example.mi4.ui.items.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mi4.data.model.Item
import com.example.mi4.data.repository.ItemRepositoryImpl
import com.example.mi4.data.repository.RoomRepositoryImpl
import com.example.mi4.data.repository.TypeRepositoryImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemListViewModel : ViewModel() {
    var itemsRepo: ItemRepositoryImpl = ItemRepositoryImpl()
    val typesRepo: TypeRepositoryImpl = TypeRepositoryImpl()
    val roomsRepo: RoomRepositoryImpl = RoomRepositoryImpl()
    var items: LiveData<List<Item>>

    init {
        items = itemsRepo.getitems()
        itemsRepo.getitems().observeForever {
            items = itemsRepo.getitems()
        }
    }

    fun deleteItem(item: Item) {
        GlobalScope.launch {
            val type = typesRepo.getType(item.type)
            val room = roomsRepo.getRoom(item.kamer)

            if(type != null && room != null){

                type.amountOfItems = type.amountOfItems.minus(1)
                room.amountOfItems = room.amountOfItems.minus(1)

                type.value = type.value - item.waarde
                room.value = room.value - item.waarde

                roomsRepo.updateRoom(room)
                typesRepo.updateType(type)
                itemsRepo.deleteItem(item)
            }


        }
    }

    companion object {
        @Volatile
        private var instance: ItemListViewModel? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: ItemListViewModel().also { instance = it }
            }
    }
}
