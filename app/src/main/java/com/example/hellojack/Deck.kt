package com.example.hellojack

class Deck {
    val cards = mutableListOf<Card>()

    init {
        for (suit in listOf("hearts", "diamonds", "clubs", "spades")) {
            for (rank in listOf(
                "ace",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "jack",
                "queen",
                "king"
            )) {
                cards.add(Card(suit, rank))

            }

        }
    }


    fun replaceFirstCard(): Card {
        cards.removeAt(0)
        val suit = listOf("hearts", "diamonds", "clubs", "spades").shuffled().first()
        val rank = listOf(
            "ace",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "jack",
            "queen",
            "king"
        ).shuffled().first()
        cards.add(0, Card(suit, rank))
        val newCard = Card(suit,rank)
        return newCard
    }
}
