package com.example.mi4.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mi4.data.db.entity.Item

@Database(
    entities = [Item::class],
    version = 1
)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile private var instance: InventoryDatabase? = null //volatile means all the threads (coroutines) have acces to this
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,InventoryDatabase::class.java,"inventory.db")
                    .build()
    }
}