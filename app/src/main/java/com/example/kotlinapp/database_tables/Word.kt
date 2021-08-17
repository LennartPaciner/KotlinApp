package com.example.kotlinapp.database_tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// room tut: https://developer.android.com/training/data-storage/room/defining-data
// coroutines basic: https://kotlinlang.org/docs/coroutines-basics.html

@Entity(tableName = "word_table")
data class Word
    (@PrimaryKey(autoGenerate = true) val id: Int = 0,
     @ColumnInfo(name = "word") val word: String) {
}