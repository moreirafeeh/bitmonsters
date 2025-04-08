package com.example.bitpets.repository

import androidx.room.*
import com.example.bitpets.room.entities.HABILIDADES
import com.example.bitpets.room.entities.HABILIDADES_PET
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO

@Dao
interface PetHabilitiesDAO {

    @Insert
    fun insert(petHability: HABILIDADES_PET) : Long

    @Query("SELECT H.NOME_HABILIDADE, H.QTD_HABILIDADE, H.DANO_HABILIDADE, H.ID_HABILIDADE, H.QTD_MAX_HABILIDADE FROM PET PT INNER JOIN HABILIDADES_PET HP ON HP.ID_PET == PT.ID_PET INNER JOIN HABILIDADES H ON H.ID_HABILIDADE = HP.ID_HABILIDADE WHERE PT.ID_PET = :id_pet")
    fun getHabilities(id_pet: Int): HABILIDADES


}