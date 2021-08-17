package com.example.kotlinapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinapp.database_daos.WordDao
import com.example.kotlinapp.database_tables.Word

// für migration zu neuer db version: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
@Database(entities = arrayOf(Word::class), version = 1)
public abstract class RoomSqlDatabase : RoomDatabase(){

    abstract fun wordDao() : WordDao

    companion object {
        // verhindert öffnen von mehreren database instanzen gleichzeitig
        @Volatile
        private var INSTANCE: RoomSqlDatabase? = null

        fun getDatabase(context: Context) : RoomSqlDatabase {
            // falls instanz != null : return, sonst erstelle
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomSqlDatabase::class.java,
                    "datenbank"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}