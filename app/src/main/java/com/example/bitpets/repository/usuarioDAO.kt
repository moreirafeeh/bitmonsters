package com.example.bitpets.repository

import androidx.room.*
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO


@Dao
interface usuarioDAO {

    @Insert
    fun insert(usuario: USUARIO) : Long

    @Update
    fun update(usuario: USUARIO): Int

    @Delete
    fun delete(usuario: USUARIO)

    @Query("SELECT * FROM USUARIO WHERE NAME = :id")
    fun get(id: Int): USUARIO

    @Query("SELECT * FROM USUARIO WHERE NAME = :name AND PASSWORD = :senha ")
    fun getLogin(name: String, senha: String ): USUARIO

    @Query("SELECT * FROM PET WHERE PET.ID_USER = :id_user ")
    fun getPetInfo(id_user: Int): PET

    @Query("SELECT * FROM USUARIO")
    fun getAll(): List<USUARIO>


}