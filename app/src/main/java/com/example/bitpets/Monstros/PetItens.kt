package com.example.bitpets.Monstros

import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class PetItens(

    var ID_ITEM: Int,
    var ID_PET: Int,
    var TYPE: MutableLiveData<String>,
    var FOME: MutableLiveData<Int>,
    var DANO:  MutableLiveData<Int>,
    var DEFESA:  MutableLiveData<Int>,
    var ITEM_IMAGE: MutableLiveData<String>

    ){

}