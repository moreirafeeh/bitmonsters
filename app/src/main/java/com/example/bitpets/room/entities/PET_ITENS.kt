package com.example.bitpets.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ ForeignKey(entity = PET::class,childColumns = ["ID_PET"],parentColumns = ["ID_PET"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE ) ])

class PET_ITENS()
    {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_ITEM")
    var ID_ITEM: Int = 0

    @ColumnInfo(name = "ID_PET")
    var ID_PET: Int = 0

    @ColumnInfo(name = "TYPE")
    var TYPE: String = ""

    @ColumnInfo(name = "FOME")
    var FOME: Int = 100

    @ColumnInfo(name = "DANO")
    var DANO: Int = 100

    @ColumnInfo(name = "DEFESA")
    var DEFESA: Int = 100

    @ColumnInfo(name = "ITEM_IMAGE")
    var ITEM_IMAGE: String = ""

    }