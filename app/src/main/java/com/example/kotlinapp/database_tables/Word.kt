package com.example.kotlinapp.database_tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// room tut: https://developer.android.com/training/data-storage/room/defining-data

@Entity(tableName = "word_table")
data class Word
    (@PrimaryKey(autoGenerate = true) val id: Int,
     @ColumnInfo(name = "word") val word: String) {

}