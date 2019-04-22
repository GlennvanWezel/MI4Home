package com.example.mi4.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mi4.data.ItemRepository
import com.example.mi4.ui.ItemsViewModel

class ItemsViewModelFactory(private val itemRepository: ItemRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsViewModel(itemRepository) as T
    }
}