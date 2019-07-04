package uk.co.vanwezel.mi4.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.co.vanwezel.mi4.data.model.Type
import uk.co.vanwezel.mi4.data.model.User

class TypeRepositoryImpl : TypeRepository {


    private var typesList = mutableListOf<Type>()

    var types = MutableLiveData<List<Type>>()

    fun getTypes(): LiveData<List<Type>> {
        return types
    }

    init {
        types.postValue(typesList)
        val fbinstance = FirebaseAuth.getInstance()
        fbinstance.addAuthStateListener {
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentTypes()
                }
            } else if (it.currentUser == null) {
                typesList = mutableListOf<Type>()
                types.postValue(typesList)
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
        typesList.remove(type)
        types.postValue(typesList)
    }

    override suspend fun addType(type: Type) {
        add(type)
    }

    suspend fun updateType(type: Type) {
        typesList[typesList.indexOf(type)] = type
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("types", typesList)
            .await()
        types.postValue(typesList)
    }


    override suspend fun getCurrentTypes() {
        val list = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .await()

        if (list.exists()) {
            typesList = list.toObject(User::class.java)!!.types.toMutableList()
            types.postValue(typesList)
        }
        //TODO: Implement a helper method that takes care of updating the typeslist and propegating changes to the livedata
    }

    //endregion

    //region helper methods
    private fun add(type: Type) {
        typesList.add(type)
        types.postValue(typesList)
        GlobalScope.launch(Dispatchers.IO) {
            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .update("types", FieldValue.arrayUnion(type))
                .await()
        }
    }

    fun getType(name: String): Type? {
        return typesList.find {
            it.name == name
        }
    }

    //endregion
    companion object {
        @Volatile
        private var instance: TypeRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance
                    ?: TypeRepositoryImpl().also { instance = it }
            }
    }
}