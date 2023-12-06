package com.example.hellojack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class WinnerLoserAnnouncement : AppCompatActivity() {

    lateinit var StartOverButton : Button
    lateinit var WinOrLoseTextView : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner_loser_announcement)


        WinOrLoseTextView = findViewById(R.id.WinOrLoseTextView)
        StartOverButton = findViewById(R.id.StartOverButton)

        val winner = intent.getStringExtra("winner")
        WinOrLoseTextView.text = if (winner == "Player") "You win!" else "Opponent wins!"


        StartOverButton. setOnClickListener{
            val intent = Intent(this, HelloJackRules::class.java)
            startActivity(intent)
            finish()
        }
    }
}