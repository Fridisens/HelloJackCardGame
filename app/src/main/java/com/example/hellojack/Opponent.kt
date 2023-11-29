package com.example.hellojack

import android.widget.TextView

class Opponent (val name: String, private val opponentCardCountView: TextView) {

    val hand: MutableList<Card> = mutableListOf()
    private  lateinit var table: Table


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

    fun makeMove() {
        if (hand.isNotEmpty()){
            val playedCard = hand.removeAt(0)
            table.addCard(playedCard)
            updateCardCount()
        }

    }
}