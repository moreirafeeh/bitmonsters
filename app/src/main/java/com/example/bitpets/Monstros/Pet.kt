package com.example.bitpets.Monstros

import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo

class Pet(

var ID_PET: Int,
var ID_USER: Int,
var PET_NAME: String,
var TYPE_PET: String,
var VIDA: MutableLiveData<Int>,
var ATAQUE: MutableLiveData<Int>,
var DEFESA: MutableLiveData<Int>,
var SEDE: MutableLiveData<Int>,
var FOME: MutableLiveData<Int>,
var PET_HOUR: String,
var QTD_COCO: Int,
var BIT_MONSTER_COIN: Int

    ){

}