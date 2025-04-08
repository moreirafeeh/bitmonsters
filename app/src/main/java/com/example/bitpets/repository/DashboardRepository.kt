package com.example.bitpets.repository

import android.content.Context
import com.example.bitpets.db.BitMonstersDataBase
import com.example.bitpets.room.entities.HABILIDADES
import com.example.bitpets.room.entities.PET

class DashboardRepository private constructor(context: Context) {

    //val bitMonsterDataBase = BitMonstersDataBase(context, 1)

    val DataBaseRoomUsuario =  BitMonstersDataBase.getDataBase(context).usuarioDAO()
    val DataBaseRoomPet =  BitMonstersDataBase.getDataBase(context).petDAO()
    val DataBaseRoomPetHability =  BitMonstersDataBase.getDataBase(context).HabilityPetDAO()

    companion object {

        private lateinit var repository: DashboardRepository

        fun getInstance(context: Context): DashboardRepository{

            if(!::repository.isInitialized){
                repository = DashboardRepository(context)
            }

            return repository
        }
    }

    fun getPetInfo(userID: String): PET {
        return DataBaseRoomUsuario.getPetInfo(userID.toInt())
    }

    fun getPetHabilitiesInfo(userID: Int): HABILIDADES {
        return DataBaseRoomPetHability.getHabilities(userID)
    }


}