package com.example.hellojack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HelloJackRules : AppCompatActivity() {

    lateinit var playButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_jack_rules)

        playButton = findViewById(R.id.playButton)


        playButton.setOnClickListener{
            val intent = Intent(this, PlayHelloJack::class.java)
            startActivity(intent)
        }

    }
}