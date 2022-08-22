package com.saxomoose.frontend.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saxomoose.frontend.entities.TransactionEntity
import com.saxomoose.frontend.entities.TransactionItemEntity

@Database(entities = [TransactionEntity::class, TransactionItemEntity::class], version = 1, exportSchema = false)
abstract class FrontEndDatabase : RoomDatabase()
{
    abstract fun transactionDao(): TransactionDao

    // Singleton pattern.
    companion object
    {
        // Annotate INSTANCE with @Volatile. The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory. This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        @Volatile
        private var INSTANCE: FrontEndDatabase? = null

        fun getDatabase(context: Context): FrontEndDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, FrontEndDatabase::class.java, "frontend")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance // return instance
                instance
            }
        }
    }
}