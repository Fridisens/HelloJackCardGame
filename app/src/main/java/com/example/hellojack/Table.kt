package com.example.hellojack

class Table {
    val cards: MutableList<Card> = mutableListOf()

    fun addCard(card:Card){
        cards.add(card)
    }

    fun clearTable(){
        cards.clear()
    }
}