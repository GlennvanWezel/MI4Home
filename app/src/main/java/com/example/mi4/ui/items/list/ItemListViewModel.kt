package com.example.mi4.ui.items.list

import android.util.Log
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
    // TODO: Implement the ViewModel
    lateinit var itemsRepo : ItemRepositoryImpl
    //lijst van objecten voor deze gebruiker
    var items : MutableLiveData<List<Item>>

    init {
        Log.i("CREATION ORDER", "ItemListViewModel | init")
        items = MutableLiveData<List<Item>>()
    }

    fun getItems() {
        GlobalScope.launch {
            val user =  FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .get()
                .await()
                .toObject(User::class.java)
            items.postValue(user?.items)
        }

    }


}
