package com.example.bitpets.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.bitpets.repository.PetHabilitiesDAO
import com.example.bitpets.repository.PetItensDAO
import com.example.bitpets.repository.petDAO
import com.example.bitpets.repository.usuarioDAO
import com.example.bitpets.room.entities.*

//------------------------------------------------CODIGO Utilizando SQLite OPENHELPER----------------
//class BitMonstersDataBase(context: Context?, version: Int) : SQLiteOpenHelper(context, NAME, null, VERSION) {
//    companion object{
//        private const val NAME = "loginDB"
//        private const val VERSION = 1
//    }
//
//
//    override fun onCreate(db: SQLiteDatabase) {
//
//        db.execSQL(
//            "create table USUARIO (ID_USER integer primary key autoincrement, NAME text, PASSWORD text, TELEFONE text, EMAIL text);"
//        )
//        db.execSQL("create table PET (ID_PET integer primary key autoincrement, ID_USER integer NOT NULL, PET_NAME text, TYPE_PET text, VIDA integer, ATAQUE integer,  DEFESA integer, SEDE integer, FOME integer, FOREIGN KEY(ID_USER) REFERENCES USUARIO(ID_USER) );")
//
//        db.execSQL("INSERT INTO USUARIO(NAME, PASSWORD, TELEFONE, EMAIL) VALUES (\"JOSELITO\", \"BATATA\", \"11965956965\", \"BATATA@HOTMAIL.COM\");")
//        db.execSQL("INSERT INTO PET(ID_USER, PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME) VALUES (1,\"Peteleco\", \"poring\", 100, 100, 100, 100, 100);")
//
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//            if(oldVersion == 1){
//                if(newVersion == 2){
//                    //ATUALIZACAO
//                }
//            }
//    }
//---------------------------------------------------------------------------------------------------


    @Database(entities = [PET::class,USUARIO::class,HABILIDADES::class, HABILIDADES_PET::class, PET_ITENS::class ], version = 11)
    abstract class BitMonstersDataBase : RoomDatabase() {

        abstract fun usuarioDAO(): usuarioDAO
        abstract fun petDAO(): petDAO
        abstract fun HabilityPetDAO(): PetHabilitiesDAO
        abstract fun PetItensDAO(): PetItensDAO

        companion object{

            private lateinit var INSTANCE: BitMonstersDataBase

            fun getDataBase(context: Context): BitMonstersDataBase{

                if(!::INSTANCE.isInitialized){
                    synchronized(BitMonstersDataBase::class.java){
                    INSTANCE = Room.databaseBuilder(context, BitMonstersDataBase::class.java, "bitMonsterDB")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                    }
                }
                return INSTANCE


            }

            private val MIGRATION_1_2: Migration = object : Migration(10, 11){
                override fun migrate(database: SupportSQLiteDatabase) {
                    //database.execSQL("INSERT INTO USUARIO(NAME, PASSWORD, TELEFONE, EMAIL, CEP, BAIRRO, RUA, NUMERO, DT_NASCIMENTO, FT_PERFIL) VALUES (\"JOSELITO\", \"BATATA\", \"11965956965\", \"BATATA@HOTMAIL.COM\", \"09930620\", \"CAMPANARIO\", \"RUA DOS MARACUJA \", \"290\", \"28/07/1996\", \"SEM FOTO\");")
                    //database.execSQL("INSERT INTO PET(ID_USER, PET_NAME, TYPE_PET, VIDA, ATAQUE, DEFESA, SEDE, FOME, PET_HOUR) VALUES (1,\"Peteleco\", \"poring\", 100, 100, 100, 100, 100, \"\");")
                    //database.execSQL("ALTER TABLE PET ADD COLUMN PET_HOUR TEXT;")

                    database.execSQL("INSERT INTO HABILIDADES(NOME_HABILIDADE, QTD_HABILIDADE, QTD_MAX_HABILIDADE, DANO_HABILIDADE) VALUES (\"FOGO\", 20, 40, 400);")
                    database.execSQL("INSERT INTO HABILIDADES(NOME_HABILIDADE, QTD_HABILIDADE, QTD_MAX_HABILIDADE, DANO_HABILIDADE) VALUES (\"GELO\", 20, 30, 200);")
                    database.execSQL("INSERT INTO HABILIDADES(NOME_HABILIDADE, QTD_HABILIDADE, QTD_MAX_HABILIDADE, DANO_HABILIDADE) VALUES (\"RAIO\", 15, 20, 500);")
                }

            }
        }


    }