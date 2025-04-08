package com.example.bitpets.ui.listener

import com.example.bitpets.ui.dashboard.CardItem

interface CardItemListener {

    fun Onclick(itemID: CardItem)
    fun OnDelete(itemID: Int)
}