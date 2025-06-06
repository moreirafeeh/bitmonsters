package com.example.bitpets.repository

import androidx.room.*
import com.example.bitpets.room.entities.HABILIDADES
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO


@Dao
interface petDAO {

    @Insert
    fun insert(pet: PET) : Long

    @Update
    fun update(pet: PET): Int

    @Delete
    fun delete(pet: PET)

    @Query("SELECT * FROM USUARIO WHERE NAME = :id")
    fun get(id: Int): USUARIO

    @Query("SELECT * FROM PET WHERE PET.ID_USER = :id_user ")
    fun getPetHour(id_user: Int): PET

    @Query("UPDATE PET SET PET_HOUR = :data_hour WHERE PET.ID_USER = :id_user ")
    fun updatePetHour(id_user: Int, data_hour: String): Int

    @Query("UPDATE PET SET QTD_COCO = :qtd_coco WHERE PET.ID_USER = :id_user ")
    fun updatePoopQuantity(id_user: Int, qtd_coco: Int): Int

    @Query("UPDATE PET SET BIT_MONSTER_COIN = :qtd_money WHERE PET.ID_USER = :id_user ")
    fun updatePetMoney(id_user: Int, qtd_money: Int): Int

    @Query("UPDATE PET SET FOME = :fome WHERE PET.ID_USER = :id_user ")
    fun updatePetHungry(id_user: Int, fome: Int): Int

    @Query("UPDATE PET SET EXPERIENCIA_PET = :exp WHERE PET.ID_USER = :id_user ")
    fun updatePetExperience(id_user: Int, exp: Int): Int

    @Query("UPDATE PET SET NIVEL_PET = :lvl WHERE PET.ID_USER = :id_user ")
    fun lvlUp(id_user: Int, lvl: Int): Int

}