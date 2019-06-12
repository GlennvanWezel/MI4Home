package com.example.mi4.ui.items.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mi4.data.model.Item
import com.example.mi4.data.repository.ItemRepositoryImpl

class ItemListViewModel : ViewModel() {
    var itemsRepo : ItemRepositoryImpl = ItemRepositoryImpl()
    var items: LiveData<List<Item>>

    init {
        items = itemsRepo.getitems()
        itemsRepo.getitems().observeForever {
            items = itemsRepo.getitems()
        }
    }
}
