package com.example.bitpets.repository

import androidx.room.*
import com.example.bitpets.room.entities.*

@Dao
interface PetItensDAO {

    @Insert
    fun insert(petItem: PET_ITENS) : Long

    @Delete
    fun delete(pet: PET_ITENS): Int

    @Query("SELECT PI.ID_ITEM, PI.ID_PET,PI.DANO, PI.DEFESA, PI.FOME, PI.TYPE, PI.ITEM_IMAGE FROM PET PT INNER JOIN PET_ITENS PI ON PI.ID_PET == PT.ID_PET WHERE PT.ID_PET = :id_pet")
    fun getPetItens(id_pet: Int): List<PET_ITENS>

}