package com.example.bitpets.ui.viewHolder

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.recyclerview.widget.RecyclerView
import com.example.bitpets.databinding.CarditemBinding
import com.example.bitpets.ui.dashboard.CardItem
import com.example.bitpets.ui.listener.CardItemListener

class CardListViewHolder internal constructor(val bind: CarditemBinding, val listener: CardItemListener) : RecyclerView.ViewHolder(bind.root) {


    fun bind(cardItem: CardItem) {

        bind.personName.setText(cardItem.itemName)
        bind.personAge.setText(cardItem.description)
        bind.personPhoto.setImageResource(cardItem.itemImage)

        bind.personPhoto.setOnClickListener{
            listener.Onclick(cardItem)
        }
    //AA00DH0MRP codigo
        bind.personPhoto.setOnLongClickListener{

            AlertDialog.Builder(itemView.context).setTitle("Delecao").setMessage("tem certeza que deseja remover?")
                .setPositiveButton("Sim", object : DialogInterface.OnClickListener{ override fun onClick(dialog: DialogInterface?, p1: Int) {
                    listener.OnDelete(cardItem.itemID)
                } })
                .setNegativeButton("Nao",object : DialogInterface.OnClickListener{ override fun onClick(dialog: DialogInterface?, p1: Int) {} })
                .create().show()

            true
        }

    }

}