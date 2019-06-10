package com.example.mi4.ui.items.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mi4.data.db.entity.Item
import com.example.mi4.data.db.entity.User
import com.example.mi4.data.repository.ItemRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ItemListViewModel : ViewModel() {
    private var itemsRepo : ItemRepositoryImpl = ItemRepositoryImpl()
    var items : LiveData<List<Item>>

    init {
        items = itemsRepo.items
        itemsRepo.items.observeForever {
            val itemsmut = MutableLiveData<List<Item>>()
            itemsmut.value = it
            items = itemsmut
        }
    }
}
