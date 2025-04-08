package com.example.bitpets.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class HABILIDADES {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_HABILIDADE")
    var ID_HABILIDADE: Int = 0

    @ColumnInfo(name = "NOME_HABILIDADE")
    var NOME_HABILIDADE: String = ""

    @ColumnInfo(name = "QTD_MAX_HABILIDADE")
    var QTD_MAX_HABILIDADE: Int = 0

    @ColumnInfo(name = "QTD_HABILIDADE")
    var QTD_HABILIDADE: Int = 0

    @ColumnInfo(name = "DANO_HABILIDADE")
    var DANO_HABILIDADE: Int = 0

}