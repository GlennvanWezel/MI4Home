package com.example.mi4.ui

import androidx.lifecycle.ViewModel
import com.example.mi4.data.Item
import com.example.mi4.data.ItemRepository

class ItemsViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    fun getItems() = itemRepository.getItems()

    fun addItem(item: Item) = itemRepository.addItem(item)
}