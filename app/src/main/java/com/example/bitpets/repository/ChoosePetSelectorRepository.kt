package com.example.bitpets.repository

import android.content.Context
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.db.BitMonstersDataBase
import com.example.bitpets.room.entities.HABILIDADES_PET
import com.example.bitpets.room.entities.PET

class ChoosePetSelectorRepository private constructor(context: Context) {

    //val bitMonsterDataBase = BitMonstersDataBase(context)

    val bitMonsterDataBase = BitMonstersDataBase.getDataBase(context).petDAO()
    val bitMonsterDataBaseHabilityPetDataBase = BitMonstersDataBase.getDataBase(context).HabilityPetDAO()


    companion object {

        private lateinit var repository: ChoosePetSelectorRepository

        fun getInstance(context: Context): ChoosePetSelectorRepository{

            if(!::repository.isInitialized){
                repository = ChoosePetSelectorRepository(context)
            }

            return repository
        }
    }

    //MODELO ROOM
        fun savePet(context: Context, PET_NAME: String, TYPE_PET: String, VIDA: Int, ATAQUE: Int, DEFESA: Int, SEDE: Int, FOME: Int ){

        var pet: PET = PET()

        pet.apply {
            this.ID_USER = SecurityPreferences(context).getUserID().toInt()
            this.PET_NAME = PET_NAME
            this.TYPE_PET = TYPE_PET
            this.VIDA = VIDA
            this.ATAQUE = ATAQUE
            this.DEFESA = DEFESA
            this.SEDE = SEDE
            this.FOME = FOME
            this.PET_HOUR = "2024-10-23T00:33:38.391862200Z"
            this.QTD_COCO =  0
            this.BIT_MONSTER_COIN = 0

        }


        bitMonsterDataBase.insert(pet)


    }

    fun getUserPet(context: Context): PET{

        return bitMonsterDataBase.getPetHour(SecurityPreferences(context).getUserID().toInt())

    }

    fun saveHability(habilidade: HABILIDADES_PET): Long {

        return bitMonsterDataBaseHabilityPetDataBase.insert(habilidade)

    }


//    fun savePet(context: Context, PET_NAME: String, TYPE_PET: String, VIDA: Int, ATAQUE: Int, DEFESA: Int, SEDE: Int, FOME: Int ){
//
//        var petModel: PetModel = PetModel()
//
//        bitMonsterDataBase.writableDatabase.execSQL("INSERT INTO PET(ID_USER, PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME) VALUES ( " + SecurityPreferences(context).getUserID() +",\"${PET_NAME}\", \"${TYPE_PET}\", ${VIDA}, ${ATAQUE}, ${DEFESA}, ${SEDE}, ${FOME});")
//
//    }
//
//    fun getInfoPet(context: Context){
//
//        var petModel: PetModel = PetModel()
//
//        bitMonsterDataBase.writableDatabase.execSQL("INSERT INTO PET(ID_USER, PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME) VALUES ( " + SecurityPreferences(context).getUserID() +",\"Peteleco\", \"poring\", 100, 100, 100, 100, 100);")
//
//    }

}