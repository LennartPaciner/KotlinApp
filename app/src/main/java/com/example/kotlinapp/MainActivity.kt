package com.example.kotlinapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.activity.NewWordActivity
import com.example.kotlinapp.adapter.WordListAdapter
import com.example.kotlinapp.database_tables.Word
import com.example.kotlinapp.viewmodels.WordViewModel
import com.example.kotlinapp.viewmodels.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

//aufbau nach dem tut: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0

// ist auch gut um nochmal durchzugehen für weiterführende doku links
// weitere zum anschauen:
// https://developer.android.com/courses/fundamentals-training/toc-v2 + https://developer.android.com/courses/fundamentals-training/overview-v2
// https://developer.android.com/codelabs/kotlin-coroutines?index=..%2F..index#0
// https://developer.android.com/training/dependency-injection
// https://developer.android.com/ + https://developer.android.com/jetpack/guide
// https://kotlinlang.org/docs/coroutines-basics.html
// even more: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#17

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        val delButton = findViewById<Button>(R.id.deleteButton)
        delButton.setOnClickListener {
            wordViewModel.deleteAllWords()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(word = it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}