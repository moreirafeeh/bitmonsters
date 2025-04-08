package com.example.bitpets.ui.dashboard

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

import android.view.LayoutInflater
import com.example.bitpets.databinding.CarditemBinding
import com.example.bitpets.ui.listener.CardItemListener
import com.example.bitpets.ui.viewHolder.CardListViewHolder


class CardItemAdapter(var cardItem: List<CardItem>) : RecyclerView.Adapter<CardListViewHolder>() {

    private lateinit var listener: CardItemListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val v = CarditemBinding.inflate( LayoutInflater.from(parent.context) , parent, false)
        return CardListViewHolder(v, listener)
    }

    override fun onBindViewHolder(viewHolder: CardListViewHolder, i: Int) {

        viewHolder.bind(cardItem[i])

    }

    override fun getItemCount(): Int {
        return cardItem.size
    }

    fun attachListener(cardListener: CardItemListener){
            listener = cardListener
    }

}