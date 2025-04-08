package com.example.bitpets.repository

import android.content.Context
import com.example.bitpets.db.BitMonstersDataBase
import com.example.bitpets.room.entities.USUARIO

class RegisterRepository(context: Context) {

    val bitMonsterDataBaseDataBase = BitMonstersDataBase.getDataBase(context).usuarioDAO()

    //val bitMonsterDataBaseDataBase = BitMonstersDataBase(context, 1)

    companion object{

        private lateinit var repository: RegisterRepository

        fun getInstance(context: Context): RegisterRepository{

            if(!::repository.isInitialized){
                repository = RegisterRepository(context)
            }

            return repository
        }
    }

      fun cadastrar(name: String, password: String, telefone: String, email: String, cep: String, bairro: String, rua: String, numero: String, dtNascimento: String, ft_perfil: String){

          var user: USUARIO = USUARIO()

          user.apply {

              user.NAME = name
              user.PASSWORD = password
              user.TELEFONE = telefone
              user.EMAIL = email
              user.CEP = cep
              user.BAIRRO = bairro
              user.RUA = rua
              user.NUMERO = numero
              user.DT_NASCIMENTO = dtNascimento
              user.FT_PERFIL = ft_perfil

          }

          bitMonsterDataBaseDataBase.insert(user)

      }


//    fun cadastrar(name: String, password: String, telefone: String, email: String){
//        bitMonsterDataBaseDataBase.writableDatabase.execSQL("INSERT INTO USUARIO(NAME, PASSWORD, TELEFONE, EMAIL) VALUES (\"${name}\", \"${password}\", \"${telefone}\", \"${email}\");")
//    }

}