package com.example.hellojack


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class PlayHelloJack : AppCompatActivity() {

    lateinit var cardFrontView: ImageView
    lateinit var deck: Deck
    lateinit var aceButton: Button
    lateinit var kingButton: Button
    lateinit var queenButton: Button
    lateinit var jackButton: Button
    lateinit var buttonFor10: Button
    lateinit var currentCard: Card

    lateinit var player: Player
    lateinit var opponent1 : Opponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_hello_jack)


        //Initialize UI elemens and the deck
        cardFrontView = findViewById(R.id.cardFrontView)
        deck = Deck()

        //Add OnClickListener for the Show card button
        val showCardButton = findViewById<Button>(R.id.showCardbutton)
        aceButton = findViewById(R.id.aceButton)
        kingButton = findViewById(R.id.kingButton)
        queenButton = findViewById(R.id.queenButton)
        jackButton = findViewById(R.id.jackButton)
        buttonFor10 = findViewById(R.id.buttonFor10)

        // Add OnClickListener for each button
        aceButton.setOnClickListener { onSpecialButtonClick("ace") }
        kingButton.setOnClickListener { onSpecialButtonClick("king") }
        queenButton.setOnClickListener { onSpecialButtonClick("queen") }
        jackButton.setOnClickListener { onSpecialButtonClick("jack") }
        buttonFor10.setOnClickListener { onSpecialButtonClick("10") }

        showCardButton.setOnClickListener {
            // Replace the current card and update the card´s image
            replaceAndShowCard()

            // Check if the new card is a special card
            if (currentCard.rank == "ace" || currentCard.rank == "10" ||
                currentCard.rank == "jack" || currentCard.rank == "queen" ||
                currentCard.rank == "king"
            ) {
                // Show the buttons for special cards
                showSpecialButtons()
            } else {
                // Hide the buttons for special cards
                hideSpecialButtons()
            }
        }
    }

    // Handle button click for special cards
    private fun onSpecialButtonClick(rank: String) {
        val latestCard = deck.cards.lastOrNull()

        if (latestCard != null && latestCard.rank == rank && :: currentCard.isInitialized) {
            // Player pressed the correct button

            val message = "You pressed the button for $rank and were the fastest!"
            showToast(message)
        } else {
            // Player pressed the wrong button

            val message = "You weren't fast enough! You get to pick up all the cards!"
            showToast(message)
        }
    }

    // Display a toast message
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    //Replace the current card and update the image
    private fun replaceAndShowCard() {
        currentCard = deck.replaceFirstCard()
        val resursID =
            resources.getIdentifier(currentCard.suit + currentCard.rank, "drawable", packageName)
        cardFrontView.setImageResource(resursID)
    }

    // Show buttons for special cards
    private fun showSpecialButtons() {
        aceButton.visibility = View.VISIBLE
        kingButton.visibility = View.VISIBLE
        queenButton.visibility = View.VISIBLE
        jackButton.visibility = View.VISIBLE
        buttonFor10.visibility = View.VISIBLE
    }

    // Hide buttons for special cards
    private fun hideSpecialButtons() {
        aceButton.visibility = View.GONE
        kingButton.visibility = View.GONE
        queenButton.visibility = View.GONE
        jackButton.visibility = View.GONE
        buttonFor10.visibility = View.GONE
    }
}