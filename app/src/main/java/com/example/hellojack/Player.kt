package com.example.hellojack

import android.widget.TextView

class Player (private val yourCardCountView: TextView) {


    var hand: MutableList<Card> = mutableListOf()


    fun receiveInitialCards(cards: List<Card>) {
        hand.clear()
        hand.addAll(cards)
        updateCardCount()
    }

    fun updateCardCount() {
        yourCardCountView.text = "${hand.size}"

    }

    fun playCard(): Card? {
        if (hand.isNotEmpty()) {
            val playedCard = hand.removeAt(0)
            updateCardCount()
            return playedCard
        }
        return null
    }

    fun addToHand(cards: List<Card>) {
        hand.addAll(cards)
        updateCardCount()
    }

    fun makeMove(selectedRank: String, currentCard: Card) {

    }

    fun pickUpCardsForLoserRound(cards: List<Card>) {
        hand.addAll(cards)
        updateCardCount()

    }

}