package com.example.hellojack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PlayHelloJack : AppCompatActivity() {

    lateinit var cardFrontView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_hello_jack)

        cardFrontView = findViewById(R.id.cardFrontView)


        val resursID = resources.getIdentifier("ace_of_clubs", "drawable", packageName)
        cardFrontView.setImageResource(resursID)




    }
}