package com.example.kotlinapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinapp.database_daos.WordDao
import com.example.kotlinapp.database_tables.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// für migration zu neuer db version: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
@Database(entities = arrayOf(Word::class), version = 1)
public abstract class RoomSqlDatabase : RoomDatabase(){

    abstract fun wordDao() : WordDao

    companion object {
        // verhindert öffnen von mehreren database instanzen gleichzeitig
        @Volatile
        private var INSTANCE: RoomSqlDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : RoomSqlDatabase {
            // falls instanz != null : return, sonst erstelle
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomSqlDatabase::class.java,
                    "datenbank"
                ).addCallback(WordDatabaseCallback(scope))
                .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            var word = Word(word = "Hello")
            wordDao.insert(word)
            word = Word(word = "World")
            wordDao.insert(word)
        }
    }
}