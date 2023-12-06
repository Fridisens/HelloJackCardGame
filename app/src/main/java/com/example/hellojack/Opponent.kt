package com.example.hellojack

import android.widget.TextView

class Opponent (private val opponentCardCountView: TextView) {

    var hand: MutableList<Card> = mutableListOf()
    private lateinit var table: Table


    fun addToHand(cards: List<Card>){
        hand.addAll(cards)
        updateCardCount()
    }


    fun setTable (table: Table){
        this.table = table
    }


    fun receiveInitialCards(cards:List<Card>){
        hand.clear()
        hand.addAll(cards)
        updateCardCount()
    }

    private fun updateCardCount() {
        opponentCardCountView.text = "${hand.size}"
    }

    fun makeMove(): Card? {
        if (hand.isNotEmpty()){
            val playedCard = hand.removeAt(0)
            //table.addCard(playedCard)
            updateCardCount()
            return playedCard
        }
        return null
    }
    fun pickUpCardsForLoserRound(cards: List<Card>) {
        hand.addAll(cards)
        updateCardCount()

    }
}