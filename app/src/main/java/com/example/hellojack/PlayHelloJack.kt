package com.example.hellojack

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hellojack.Deck
import com.example.hellojack.R

class PlayHelloJack : AppCompatActivity() {

    lateinit var cardFrontView: ImageView
    lateinit var deck: Deck
    lateinit var aceButton: Button
    lateinit var kingButton: Button
    lateinit var queenButton: Button
    lateinit var jackButton: Button
    lateinit var buttonFor10: Button
    lateinit var currentCard: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_hello_jack)

        cardFrontView = findViewById(R.id.cardFrontView)
        deck = Deck()

        val showCardButton = findViewById<Button>(R.id.showCardbutton)
        aceButton = findViewById(R.id.aceButton)
        kingButton = findViewById(R.id.kingButton)
        queenButton = findViewById(R.id.queenButton)
        jackButton = findViewById(R.id.jackButton)
        buttonFor10 = findViewById(R.id.buttonFor10)

        // Lägg till OnClickListener för varje knapp
        aceButton.setOnClickListener { onSpecialButtonClick("ace") }
        kingButton.setOnClickListener { onSpecialButtonClick("king") }
        queenButton.setOnClickListener { onSpecialButtonClick("queen") }
        jackButton.setOnClickListener { onSpecialButtonClick("jack") }
        buttonFor10.setOnClickListener { onSpecialButtonClick("10") }

        showCardButton.setOnClickListener {
            // Byt kort och uppdatera kortets bild
            replaceAndShowCard()

            // Kolla om det nya kortet är ett specialkort
            if (currentCard.rank == "ace" || currentCard.rank == "10" ||
                currentCard.rank == "jack" || currentCard.rank == "queen" ||
                currentCard.rank == "king"
            ) {
                // Visa knapparna för specialkort
                showSpecialButtons()
            } else {
                // Dölj knapparna för specialkort
                hideSpecialButtons()
            }
        }
    }

    private fun onSpecialButtonClick(rank: String) {
        val latestCard = deck.cards.lastOrNull()

        if (latestCard != null && latestCard.rank == rank && :: currentCard.isInitialized) {
            // Spelaren tryckte på rätt knapp
            // Utför åtgärder här
            // Exempel: visa ett meddelande, uppdatera poäng, etc.

            val message = "Du tryckte på knappen för $rank och var snabbast!"
            showToast(message)
        } else {
            // Spelaren tryckte på fel knapp
            // Utför åtgärder här om det behövs

            val message = "Du var inte tillräckligt snabb! Du får ta upp alla korten!"
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun replaceAndShowCard() {
        currentCard = deck.replaceFirstCard()
        val resursID =
            resources.getIdentifier(currentCard.suit + currentCard.rank, "drawable", packageName)
        cardFrontView.setImageResource(resursID)
    }

    private fun showSpecialButtons() {
        aceButton.visibility = View.VISIBLE
        kingButton.visibility = View.VISIBLE
        queenButton.visibility = View.VISIBLE
        jackButton.visibility = View.VISIBLE
        buttonFor10.visibility = View.VISIBLE
    }

    private fun hideSpecialButtons() {
        aceButton.visibility = View.GONE
        kingButton.visibility = View.GONE
        queenButton.visibility = View.GONE
        jackButton.visibility = View.GONE
        buttonFor10.visibility = View.GONE
    }
}