package uk.co.vanwezel.mi4.ui

import androidx.lifecycle.ViewModel
import uk.co.vanwezel.mi4.data.model.Item
import uk.co.vanwezel.mi4.data.repository.ItemRepositoryImpl

class ItemsViewModel(private val itemRepository: ItemRepositoryImpl) : ViewModel() {
    //fun getItems() = itemRepository.getCurrentItems()

    fun addItem(item: Item) = itemRepository.addItem(item)
}