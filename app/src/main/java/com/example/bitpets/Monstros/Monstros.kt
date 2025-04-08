package com.example.bitpets.Monstros

import androidx.lifecycle.MutableLiveData

class Monstros(

    var nome: String,
    var vida: MutableLiveData<Int>,
    var ataque: MutableLiveData<Int>,
    var image: MutableLiveData<Int>

    ){

}