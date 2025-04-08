package com.example.bitpets.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ ForeignKey(entity = USUARIO::class,childColumns = ["ID_USER"],parentColumns = ["ID_USER"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE ) ])

class PET(
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_PET")
    var ID_PET: Int = 0

    @ColumnInfo(name = "ID_USER")
    var ID_USER: Int = 0

    @ColumnInfo(name = "PET_NAME")
    var PET_NAME: String =""

    @ColumnInfo(name = "TYPE_PET")
    var TYPE_PET: String = ""

    @ColumnInfo(name = "VIDA")
    var VIDA: Int = 0

    @ColumnInfo(name = "ATAQUE")
    var ATAQUE: Int= 0

    @ColumnInfo(name = "DEFESA")
    var DEFESA: Int = 0

    @ColumnInfo(name = "SEDE")
    var SEDE: Int = 0

    @ColumnInfo(name = "FOME")
    var FOME: Int = 0

    @ColumnInfo(name = "PET_HOUR")
    var PET_HOUR: String = ""

    @ColumnInfo(name = "QTD_COCO")
    var QTD_COCO: Int = 0

    @ColumnInfo(name = "BIT_MONSTER_COIN")
    var BIT_MONSTER_COIN: Int = 0
}