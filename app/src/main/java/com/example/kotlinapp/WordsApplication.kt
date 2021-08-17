package com.example.kotlinapp

import android.app.Application
import com.example.kotlinapp.database.RoomSqlDatabase
import com.example.kotlinapp.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { RoomSqlDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }

}