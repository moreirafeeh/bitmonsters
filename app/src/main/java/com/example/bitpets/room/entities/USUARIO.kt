package com.example.bitpets.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class USUARIO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USER")
    var ID_USER: Int = 0

    @ColumnInfo(name = "NAME")
    var NAME: String = ""

    @ColumnInfo(name = "PASSWORD")
    var PASSWORD: String = ""

    @ColumnInfo(name = "TELEFONE")
    var TELEFONE: String = ""

    @ColumnInfo(name = "EMAIL")
    var EMAIL: String =  ""

    @ColumnInfo(name = "CEP")
    var CEP: String =  ""

    @ColumnInfo(name = "BAIRRO")
    var BAIRRO: String =  ""

    @ColumnInfo(name = "RUA")
    var RUA: String =  ""

    @ColumnInfo(name = "NUMERO")
    var NUMERO: String =  ""

    @ColumnInfo(name = "DT_NASCIMENTO")
    var DT_NASCIMENTO: String =  ""

    @ColumnInfo(name = "FT_PERFIL")
    var FT_PERFIL: String =  ""


}