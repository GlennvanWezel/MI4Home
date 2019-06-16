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
import uk.co.vanwezel.mi4.data.model.Room
import uk.co.vanwezel.mi4.data.model.User

class RoomRepositoryImpl : RoomRepository {
    private var roomsList = mutableListOf<Room>()

    var rooms = MutableLiveData<List<Room>>()

    fun getRooms(): LiveData<List<Room>> {
        return rooms
    }


    init {
        //Make sure the rooms liveData has something in it
        rooms.postValue(roomsList)
        //get an instance of firebaseAuth
        val fbainstance = FirebaseAuth.getInstance()

        //add a state listener, this is to prevent the previous users's items from being kept when
        //a logout occurs
        //also to ensure the new items are fetched whenever a new user logs in
        fbainstance.addAuthStateListener {
            //No matter what, this listener is called when the user changes, thus clear the 'cached' items
            clear()

            //if the user is simply logged out, currentUser would = null
            //so if that's not the case, then someone is logged in. get their currentRooms
            if (it.currentUser != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    getCurrentRooms()
                }
            } else if (it.currentUser == null) {
                //placeholder for when I find a use for this continuity
            }
        }
    }

    //region CRUD METHODS

    /**
     *
     */
    fun updateRoom(room: Room) {
        update(room)
        save()
    }

    /**
     *
     */
    override suspend fun deleteRoom(room: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayRemove(room))
            .await()
        remove(room)
    }

    /**
     * Adds a given room to the Firestore and the repository lists,
     * preventing the need for an extra read from the Firestore
     * @author
     */
    override suspend fun addRoom(room: Room) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .update("rooms", FieldValue.arrayUnion(room))
            .await()
        add(room)
    }

    /**
     * Attempts to get the rooms of the current user from firestore,
     * if not successful it will not crash
     * @author Glenn van Wezel
     */
    override suspend fun getCurrentRooms() {
        val currentRooms = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .await()

        if (currentRooms != null) {
            updateRooms(currentRooms.toObject(User::class.java)!!.rooms.toMutableList())
        }
    }

    //region newTest

    /**
     * adds a room to the list
     * and posts the list to the LiveData
     * @author Glenn van Wezel
     */
    private fun add(room: Room){
        roomsList.add(room)
        rooms.postValue(roomsList)
    }

    /**
     * Removes a room from the roomsList
     * and then posts the value to rooms LiveData
     * @param room : the room to be removed from the list
     * @author Glenn van Wezel
     */
    private fun remove(room: Room){
        roomsList.remove(room)
        save()
    }

    /**
     * Removes all rooms within the given list from the roomsList
     * and then post the changes to rooms LiveData
     * @author Glenn van Wezel
     * @param list: a list of rooms to be deleted
     */
    private fun removeAll(list: List<Room>){
        list.forEach {
            remove(it)
        }
        save()
    }

    private fun save(){
        rooms.postValue(roomsList)
    }

    /**
     * \Updates a single given room in the roomsList and posts the changes to rooms LiveData
     * @author Glenn van Wezel
     * @param room the room to be updated
     */
    private fun update(room: Room){
        roomsList[roomsList.indexOf(room)] = room
        GlobalScope.launch {
            FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .update("rooms", roomsList)
                .await()
        }
    }


    /**
     * Updates the Repository lists and LiveData with the given List
     * @author Glenn van Wezel
     */
    private fun updateRooms(list: MutableList<Room>) {
        roomsList = list
        rooms.postValue(roomsList)
    }

    /**
     * Clears the Repository lists and LiveData
     * @author Glenn van Wezel
     */
    private fun clear(){
        roomsList = mutableListOf<Room>()
        rooms.postValue(roomsList)
    }

//endregion
//endregion


    fun getRoom(name: String): Room? {
        return roomsList.find {
            it.name == name
        }
    }

    companion object {
        @Volatile
        private var instance: RoomRepositoryImpl? = null

        fun invoke() =
            instance ?: synchronized(this) {
                instance
                    ?: RoomRepositoryImpl().also { instance = it }
            }
    }
}