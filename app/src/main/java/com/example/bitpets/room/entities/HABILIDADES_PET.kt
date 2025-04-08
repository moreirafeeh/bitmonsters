package com.example.bitpets.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["ID_HABILIDADE", "ID_PET"], foreignKeys = [ ForeignKey(entity = PET::class,childColumns = ["ID_PET"],parentColumns = ["ID_PET"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE ),
                                                                   ForeignKey(entity = HABILIDADES::class,childColumns = ["ID_HABILIDADE"],parentColumns = ["ID_HABILIDADE"], onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)])
class HABILIDADES_PET {

    @ColumnInfo(name = "ID_HABILIDADE")
    var ID_HABILIDADE: Int = 0


    @ColumnInfo(name = "ID_PET")
    var ID_PET: Int = 0


}