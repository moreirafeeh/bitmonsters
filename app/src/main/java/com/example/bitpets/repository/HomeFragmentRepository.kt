package com.example.bitpets.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.db.BitMonstersDataBase
import com.example.bitpets.models.PetModel
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.PET_ITENS

class HomeFragmentRepository  private constructor(context: Context) {

    //val bitMonsterDataBase = BitMonstersDataBase(context, 1)

    val loginDataBaseRoom =  BitMonstersDataBase.getDataBase(context).usuarioDAO()
    val loginDataBaseRoom2 =  BitMonstersDataBase.getDataBase(context).petDAO()
    private val petItensDataBaseRoom =  BitMonstersDataBase.getDataBase(context).PetItensDAO()

    companion object {

        private lateinit var repository: HomeFragmentRepository

        fun getInstance(context: Context): HomeFragmentRepository{

            if(!::repository.isInitialized){
                repository = HomeFragmentRepository(context)
            }

            return repository
        }
    }

    fun getPetInfo(userID: String): PET {
        return loginDataBaseRoom.getPetInfo(userID.toInt())
    }

    fun updatePetInfoHour(userID: String, data: String): Int {
        return loginDataBaseRoom2.updatePetHour(userID.toInt(), data)
    }

    fun updatePetPoopQuantity(userID: String, data: Int): Int {
        return loginDataBaseRoom2.updatePoopQuantity(userID.toInt(), data)
    }

    fun updatePetMoney(userID: String, money: Int): Int {
        return loginDataBaseRoom2.updatePetMoney(userID.toInt(), money)
    }

    fun updatePetHungry(userID: String, fome: Int): Int {
        return loginDataBaseRoom2.updatePetHungry(userID.toInt(), fome)
    }

    fun insert(petItens: PET_ITENS): Boolean{
        return petItensDataBaseRoom.insert(petItens) > 0
    }

    fun getPetItensInfo(id_pet: String): List<PET_ITENS> {
        return petItensDataBaseRoom.getPetItens(id_pet.toInt())
    }

    fun deletePetItem(pet_Item: PET_ITENS): Boolean {
        return petItensDataBaseRoom.delete(pet_Item) > 0
    }


//    @SuppressLint("Range")
//    fun getPetInfo(userID: String): PetModel {
//        var petModel  = PetModel()
//
//        val db = bitMonsterDataBase.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM PET WHERE ID_USER = ${userID} ;", null, null)
//        try {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                if (cursor.getCount() > 0) {
//                    do {
//                        petModel.ID_USER = cursor.getInt(cursor.getColumnIndex("ID_USER"))
//                        petModel.PET_NAME = cursor.getString(cursor.getColumnIndex("PET_NAME"))
//                        petModel.TYPE_PET = cursor.getString(cursor.getColumnIndex("TYPE_PET"))
//                        petModel.VIDA = cursor.getInt(cursor.getColumnIndex("VIDA"))
//                        petModel.ATAQUE = cursor.getInt(cursor.getColumnIndex("ATAQUE"))
//                        petModel.DEFESA = cursor.getInt(cursor.getColumnIndex("DEFESA"))
//                        petModel.SEDE = cursor.getInt(cursor.getColumnIndex("SEDE"))
//                        petModel.FOME = cursor.getInt(cursor.getColumnIndex("FOME"))
//
//                    } while ((cursor.moveToNext()));
//                }
//            }
//        } finally {
//            cursor.close();
//        }
//
//        petModel
//
//        return petModel
//    }

}