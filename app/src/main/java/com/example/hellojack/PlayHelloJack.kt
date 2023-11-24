package com.example.hellojack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class PlayHelloJack : AppCompatActivity() {

    lateinit var cardFrontView: ImageView
    lateinit var deck: Deck


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_hello_jack)

        cardFrontView = findViewById(R.id.cardFrontView)
        deck = Deck()


        val showCardButton = findViewById<Button>(R.id.showCardbutton)
        showCardButton.setOnClickListener {
            deck.replaceFirstCard()
            val card = deck.cards[0]

            val resursId = resources.getIdentifier(card.suit + card.rank, "drawable", packageName )
            cardFrontView.setImageResource(resursId)

        }
    }
}