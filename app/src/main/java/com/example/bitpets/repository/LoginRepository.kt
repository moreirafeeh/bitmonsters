package com.example.bitpets.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.bitpets.db.BitMonstersDataBase
import com.example.bitpets.models.LoginModel
import com.example.bitpets.models.PetModel
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO

class LoginRepository(context: Context){

    //val loginDataBase = BitMonstersDataBase(context, 1)

    val loginDataBaseRoom =  BitMonstersDataBase.getDataBase(context).usuarioDAO()


    companion object {
        private lateinit var repository: LoginRepository

        fun getInstance(context: Context): LoginRepository{

            if(!::repository.isInitialized){
                repository = LoginRepository(context)
            }

            return repository
        }
    }


    fun insert(login: USUARIO): Boolean{
        return loginDataBaseRoom.insert(login) > 0
    }

    fun getuserInfo(name: String, password: String): USUARIO{
         return loginDataBaseRoom.getLogin(name, password)
    }

    fun getPetInfo(idUser: Int): PET {
        return loginDataBaseRoom.getPetInfo(idUser)
    }


//    fun logar(usuario: String, senha: String): LoginModel {
//
//        var loginModel: LoginModel = LoginModel(0 ,"","","","")
//
//        val ResultDBcursor = loginDataBase.readableDatabase.rawQuery("SELECT * FROM USUARIO WHERE NAME = \"${usuario}\" AND PASSWORD = \"${senha}\" ;", null)
//
//
//
//        if(ResultDBcursor != null ){
//
//        }
//
//
//        val args = arrayOf(
//            "ID_USER","NAME", "PASSWORD"
//
//        )
//
//
//        loginModel
//
//        return loginModel
//
//    }
//
//
//    @SuppressLint("Range")
//    fun getParticularUserData(usuario: String, senha: String): LoginModel {
//        var loginModel  = LoginModel()
//        val db = loginDataBase.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM USUARIO WHERE NAME = \"${usuario}\" AND PASSWORD = \"${senha}\" ;", null, null)
//        try {
//            if (cursor.getCount() != 0) {
//                cursor.moveToFirst();
//                if (cursor.getCount() > 0) {
//                    do {
//                        loginModel.ID_USER = cursor.getInt(cursor.getColumnIndex("ID_USER"))
//                        loginModel.NAME = cursor.getString(cursor.getColumnIndex("NAME"))
//                        loginModel.PASSWORD = cursor.getString(cursor.getColumnIndex("PASSWORD"))
//                        loginModel.TELEFONE = cursor.getString(cursor.getColumnIndex("TELEFONE"))
//                        loginModel.EMAIL = cursor.getString(cursor.getColumnIndex("EMAIL"))
//                    } while ((cursor.moveToNext()));
//                }
//            }
//        } finally {
//            cursor.close();
//        }
//
//        loginModel
//
//        return loginModel
//    }
//
//    @SuppressLint("Range")
//    fun getPetInfo(userID: String): PetModel {
//        var petModel  = PetModel()
//
//        val db = loginDataBase.readableDatabase
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