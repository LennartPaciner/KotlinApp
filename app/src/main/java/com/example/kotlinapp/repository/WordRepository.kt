package com.example.kotlinapp.repository

import androidx.annotation.WorkerThread
import com.example.kotlinapp.database_daos.WordDao
import com.example.kotlinapp.database_tables.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}