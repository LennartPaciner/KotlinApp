package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//tut: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}