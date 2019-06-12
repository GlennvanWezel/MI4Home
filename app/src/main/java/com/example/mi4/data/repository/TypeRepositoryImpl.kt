package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.model.Type
import com.example.mi4.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TypeRepositoryImpl : TypeRepository {


    private var typesList = mutableListOf<Type>()

    var types = MutableLiveData<List<Type>>()

    fun getTypes(): LiveData<List<Type>> {
        return typesList as LiveData<List<Type>>
    }

    init {
        types.value = typesList
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentTypes()
                }
            } else if (it.currentUser == null) {
                typesList = mutableListOf<Type>()
                types.value = typesList
            }
        }
    }

    //region CRUD METHODS
    override suspend fun deleteType(type: Type) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("types", FieldValue.arrayRemove(type))
            .await()
    }

    override suspend fun addType(type: Type) {
        add(type)
    }

    override suspend fun updateType(oldType: Type, newType: Type) {
        deleteType(oldType)
        addType(newType)
    }


    override suspend fun getCurrentTypes() {
        typesList = (
                FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid)
                    .get(Source.CACHE)
                    .await()
                    .toObject(User::class.java)!!
                    .types
                ).toMutableList()
        types.postValue(typesList)
    }

    //endregion

    //region helper methods
    private fun add(type: Type) {
        typesList.add(type)
        types.value = typesList
        GlobalScope.launch(Dispatchers.IO){
            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .update("types", FieldValue.arrayUnion(type))
                .await()
        }
    }

    //endregion
    companion object {
        @Volatile
        private var instance: TypeRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance ?: TypeRepositoryImpl().also { instance = it }
            }
    }
}