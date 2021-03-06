package uk.co.vanwezel.mi4.ui.items.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uk.co.vanwezel.mi4.data.repository.ItemRepositoryImpl
import uk.co.vanwezel.mi4.ui.ItemsViewModel

class ItemsListViewModelFactory(private val itemRepository: ItemRepositoryImpl) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsViewModel(ItemRepositoryImpl()) as T
    }
}