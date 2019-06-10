package com.example.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mi4.data.db.entity.Type
import com.example.mi4.data.db.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TypeRepositoryImpl : TypeRepository {


    private var typesList = MutableLiveData<List<Type>>()

    var types: LiveData<List<Type>>
        get() {
            return typesList
        }

    init {
        types = typesList
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentTypes()
                }
            } else if (it.currentUser == null) {
                typesList = MutableLiveData<List<Type>>()
                types = typesList
            }
        }
    }

    //region CRUD METHODS
    override suspend fun deleteType(type: Type) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("types",FieldValue.arrayRemove(type))
            .await()
    }

    override suspend fun addType(type: Type) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("types",FieldValue.arrayUnion(type))
            .await()
    }

    override suspend fun updateType(oldType: Type, newType: Type) {
        deleteType(oldType)
        addType(newType)
    }


    override suspend fun getCurrentTypes() {
        typesList.postValue(FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get(Source.CACHE)
            .await()
            .toObject(User::class.java)!!
            .types)
        types = typesList
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