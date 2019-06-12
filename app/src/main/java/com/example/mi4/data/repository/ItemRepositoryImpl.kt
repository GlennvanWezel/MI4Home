package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.model.Item
import com.example.mi4.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ItemRepositoryImpl : ItemRepository {

    private var itemsList = mutableListOf<Item>()
    private var items = MutableLiveData<List<Item>>()

    fun getitems() = items as LiveData<List<Item>>


    init {
        items.value = itemsList
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentItems()
                }
            } else if (it.currentUser == null) {
                itemsList = mutableListOf<Item>()
                items.value = itemsList
            }
        }
    }

    //region CRUD METHODS
    override suspend fun deleteItem(item: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayRemove(item))
            .await()
    }

    override suspend fun updateItem(olditem: Item, newitem: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayRemove(olditem))
            .await()
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayUnion(newitem))
            .await()
    }


    override fun addItem(item: Item) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("items", FieldValue.arrayUnion(item))
    }

    override suspend fun getCurrentItems() {
        itemsList = (FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .await()
            .toObject(User::class.java)!!
            .items).toMutableList()
        items.postValue(itemsList)
    }

    //endregion

    companion object {
        @Volatile
        private var instance: ItemRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: ItemRepositoryImpl().also { instance = it }
            }
    }
}