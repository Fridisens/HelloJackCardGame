package com.example.hellojack


import android.content.Intent
import com.example.hellojack.Player
import com.example.hellojack.Opponent
import com.example.hellojack.Table
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.util.Log


class PlayHelloJack : AppCompatActivity() {

    lateinit var cardFrontView: ImageView
    lateinit var deck: Deck
    lateinit var aceButton: Button
    lateinit var kingButton: Button
    lateinit var queenButton: Button
    lateinit var jackButton: Button
    lateinit var buttonFor10: Button
    lateinit var currentCard: Card

    lateinit var yourCardCountView : TextView
    lateinit var opponentCardCountView : TextView
    lateinit var onTableCardCountView : TextView

    lateinit var player: Player
    lateinit var opponent: Opponent
    lateinit var table: Table

    private var isPlayerTurn = true
    private var opponentChoice: String = ""
    private var tableCardCount: Int = 0
    private val handler = Handler(Looper.getMainLooper())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_hello_jack)


        // Initialize UI elements and the deck
        cardFrontView = findViewById(R.id.cardFrontView)
        deck = Deck()


        //Add OnClickListener for the Special card-button
        val showCardButton = findViewById<Button>(R.id.showCardbutton)
        aceButton = findViewById(R.id.aceButton)
        kingButton = findViewById(R.id.kingButton)
        queenButton = findViewById(R.id.queenButton)
        jackButton = findViewById(R.id.jackButton)
        buttonFor10 = findViewById(R.id.buttonFor10)

        yourCardCountView = findViewById(R.id.yourCardCountView)
        opponentCardCountView = findViewById(R.id.opponentCardCountView)
        onTableCardCountView = findViewById(R.id.onTableCardCountView)


        // Add OnClickListener for each Special Card-button
        aceButton.setOnClickListener { onSpecialButtonClick("ace") }
        kingButton.setOnClickListener { onSpecialButtonClick("king") }
        queenButton.setOnClickListener { onSpecialButtonClick("queen") }
        jackButton.setOnClickListener { onSpecialButtonClick("jack") }
        buttonFor10.setOnClickListener { onSpecialButtonClick("10") }

        table = Table()
        opponent = Opponent(opponentCardCountView)
        opponent.setTable(table)

        player = Player (yourCardCountView)
        dealInitialCards()

        //placeCardOnTable()

        var playerLastCardSpecial = false
        showCardButton.setOnClickListener {
            // Replace the current card and update the card´s image

            if (!playerLastCardSpecial) {
                replaceAndShowCard()
                placeCardOnTablePlayer()

                // Check if the new card is a special card
                if (currentCard.rank == "ace" || currentCard.rank == "10" ||
                    currentCard.rank == "jack" || currentCard.rank == "queen" ||
                    currentCard.rank == "king"
                ) {
                    // Show the buttons for special cards
                    playerLastCardSpecial = true
                    showSpecialButtons()
                    return@setOnClickListener
                } else {
                    // Hide the buttons for special cards
                    playerLastCardSpecial = false
                    hideSpecialButtons()
                }
            }
            playerLastCardSpecial = false

            //player.makeMove("some_rank", currentCard)


            // Update UI after my own move
            onTableCardCountView.text = "${table.cards.size}"
            yourCardCountView.text = "${player.hand.size}"


            if (player.hand.isEmpty() || opponent.hand.isEmpty()){
                checkForWinner()
                return@setOnClickListener
            }
            // delay opponents move to push special card-button between 1-10 sec
            val delayInMillis = (1..5).random() * 1000L

            // Next activity when we reach a winner
//            if (player.hand.isEmpty() || opponent.hand.isEmpty()) {
//                val winnerIntent = Intent(this, WinnerLoserAnnouncement::class.java)
//                winnerIntent.putExtra("winner", if (player.hand.isEmpty()) "Player" else "Opponent")
//                startActivity(winnerIntent)
//                finish()
//                return@setOnClickListener
//            }

            //cardsPlayedThisRound++
            //if (cardsPlayedThisRound == 1) {
            //cardsPlayedThisRound = 0


            onTableCardCountView.text = "${table.cards.size}"

            // Place the card with 1 sec delay
            handler.postDelayed({
                onTableCardCountView.text = "${table.cards.size}"

                // Opponents move with delay
                handler.postDelayed({
                    //replaceAndShowCard()
                    // Replace the current card and update the card´s image
                    replaceAndShowCard()
                    placeCardOnTableOpponent()

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

                    checkForWinner()

                    // Uppdatera UI efter motståndarens drag
                    onTableCardCountView.text = "${table.cards.size}"
                    opponentCardCountView.text = "${opponent.hand.size}"

                    handler.postDelayed({

                    }, delayInMillis)
                }, 1000)
            }, 1000)
            //}
        }
    }

    // Handle button click for special cards
    private fun onSpecialButtonClick(rank: String) {
        val latestCard = deck.cards.lastOrNull()

        if (latestCard != null && latestCard.rank == rank && ::currentCard.isInitialized) {
            // Player or opponent pressed the right button
            val message = if (isPlayerTurn) {
                "You pressed the button for $rank and were the fastest!"
            } else {
                "Opponent pressed the button for $rank and was the fastest!"
            }
            showToast(message)

            //New test for pick up the cards on table
            if (isPlayerTurn) {
                player.addToHand (table.cards)
                yourCardCountView.text = "${player.hand.size}"
            }else {
                opponent.addToHand(table.cards)
                opponentCardCountView.text = "${opponent.hand.size}"

            }

            table.clearTable()
            onTableCardCountView.text = "0"

        } else {
            // Player or opponent pressed the wrong button
            val message = if (isPlayerTurn) {
                "To slow, You have to pick up the cards on the table"
            } else {
                "Opponent were to slow, they have to pick up the cards on the table"
            }
            showToast(message)

            pickUpTheCardsForLoserRound()
        }

        // Update Boolean for who´s turn it is
        isPlayerTurn = !isPlayerTurn
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

    private fun dealInitialCards(){
        val playerCards = mutableListOf<Card>()
        val opponentCards = mutableListOf<Card>()

        //Divide the deck between players
        val playerHandSize = deck.cards.size / 2
        playerCards.addAll(deck.cards.take(playerHandSize))
        opponentCards.addAll(deck.cards.takeLast(playerHandSize))

        tableCardCount = 0

        // Assign card to player and opponent
        player.receiveInitialCards(playerCards)
        opponent.receiveInitialCards(opponentCards)

        //Update how many cards in each "hand"
        yourCardCountView.text = "${playerCards.size}"
        opponentCardCountView.text = "${opponentCards.size}"

    }

    private fun placeCardOnTablePlayer(){
        val playedCard = player.playCard()
        if(playedCard!= null){
            table.addCard(playedCard)

            yourCardCountView.text = "${player.hand.size}"
            onTableCardCountView.text = "${table.cards.size}"
        }


    }

    private fun placeCardOnTableOpponent() {
        val opponentPlayedCard = opponent.makeMove()
        if(opponentPlayedCard!= null){
            table.addCard(opponentPlayedCard)

            opponentCardCountView.text = "${opponent.hand.size}"
            onTableCardCountView.text = "${table.cards.size}"
        }
    }


//    private fun simulateOpponentChoice() {
//
//        if (opponentChoice.isEmpty()) {
//            val rankValues = listOf("ace", "king", "queen", "jack", "10")
//            opponentChoice = rankValues.random()
//
//            if(!isPlayerTurn){
//                onSpecialButtonClick(opponentChoice)
//
//            }
//
//     }

    //}



    private fun pickUpTheCardsForLoserRound(){

        if (isPlayerTurn){
            player.pickUpCardsForLoserRound(table.cards)
            yourCardCountView.text = "${player.hand.size}"
        } else {
            opponent.pickUpCardsForLoserRound(table.cards)
            opponentCardCountView.text = "${opponent.hand.size}"
        }
        table.clearTable()
        onTableCardCountView.text = "0"

    }

    private fun checkForWinner() {
        if (player.hand.isEmpty() || opponent.hand.isEmpty()) {
            val winnerIntent = Intent(this, WinnerLoserAnnouncement::class.java)
            winnerIntent.putExtra("winner", if (player.hand.isEmpty()) "Player" else "Opponent")
            startActivity(winnerIntent)
            finish()
        }
    }

}
