package com.example.mi4.utilities

import com.example.mi4.data.FakeDatabase
import com.example.mi4.data.ItemRepository
import com.example.mi4.ui.items.ItemsViewModelFactory

object InjectorUtils {

    fun provideItemsViewModelFactory(): ItemsViewModelFactory{
        val itemRepository = ItemRepository.getInstance(FakeDatabase.getInstance().itemDao)
        return ItemsViewModelFactory(itemRepository)
    }
}