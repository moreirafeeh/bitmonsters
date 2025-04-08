package com.example.bitpets.ui.viewHolder

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.recyclerview.widget.RecyclerView
import com.example.bitpets.databinding.BagitemBinding
import com.example.bitpets.databinding.CarditemBinding
import com.example.bitpets.ui.dashboard.CardItem
import com.example.bitpets.ui.dashboard.EquipedItem
import com.example.bitpets.ui.listener.CardItemListener
import com.example.bitpets.ui.listener.EquipedItemListener

class EquipedItemViewHolder internal constructor(val bind: BagitemBinding, val listener: EquipedItemListener) : RecyclerView.ViewHolder(bind.root)  {

    fun bind(equipedItem: EquipedItem) {

        bind.personPhoto.setImageResource(equipedItem.itemImage)

        bind.personPhoto.setOnClickListener{
            listener.Onclick(equipedItem.itemImage)
        }

        bind.personPhoto.setOnLongClickListener{

            AlertDialog.Builder(itemView.context).setTitle("Delecao").setMessage("tem certeza que deseja remover?")
                .setPositiveButton("Sim", object : DialogInterface.OnClickListener{ override fun onClick(dialog: DialogInterface?, p1: Int) {
                    listener.OnDelete(equipedItem.itemID)
                } })
                .setNegativeButton("Nao",object : DialogInterface.OnClickListener{ override fun onClick(dialog: DialogInterface?, p1: Int) {} })
                .create().show()

            true
        }

    }

}