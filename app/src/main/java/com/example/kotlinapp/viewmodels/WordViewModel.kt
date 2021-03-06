package com.example.kotlinapp.viewmodels

import androidx.lifecycle.*
import com.example.kotlinapp.database_tables.Word
import com.example.kotlinapp.repository.WordRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository) : ViewModel(){

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    //delete
    fun deleteAllWords() = viewModelScope.launch {
        repository.deleteAllWords()
    }

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}


class WordViewModelFactory(private val repository: WordRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}