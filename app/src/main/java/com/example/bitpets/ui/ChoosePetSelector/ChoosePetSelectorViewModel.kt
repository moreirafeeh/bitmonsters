package com.example.bitpets.ui.ChoosePetSelector

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.content.Context
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.repository.ChoosePetSelectorRepository
import com.example.bitpets.repository.LoginRepository
import com.example.bitpets.room.entities.HABILIDADES_PET
import com.example.bitpets.room.entities.PET

class ChoosePetSelectorViewModel(application: Application): AndroidViewModel(application) {

      private val repository = ChoosePetSelectorRepository.getInstance(application)

//
//    fun savePet(context: Context, PET_NAME: String, TYPE_PET: String, VIDA: Int, ATAQUE: Int, DEFESA: Int, SEDE: Int, FOME: Int ){
//
//        repository.savePet(getApplication(), PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME)
//
//    }

    // MODELO ROOM
    fun savePet(context: Context, PET_NAME: String, TYPE_PET: String, VIDA: Int, ATAQUE: Int, DEFESA: Int, SEDE: Int, FOME: Int ){

        repository.savePet(getApplication(), PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME)

    }

    fun getPetUser(context: Context): PET {

        var pet = repository.getUserPet(getApplication())

        return pet

    }

    fun saveHabilities( habilidade: HABILIDADES_PET){

        repository.saveHability(habilidade)

    }

}