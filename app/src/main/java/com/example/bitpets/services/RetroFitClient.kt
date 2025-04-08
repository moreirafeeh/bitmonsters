package com.example.bitpets.services

import com.example.bitpets.models.CepEntity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitClient {



    companion object{

        private lateinit var INSTANCE: Retrofit
        private var BASE_URL = "https://viacep.com.br/"

        private fun getRetrofitInstance(): Retrofit{
            val http = OkHttpClient.Builder()
            if(!::INSTANCE.isInitialized){
                INSTANCE = Retrofit.Builder().baseUrl(BASE_URL).client(http.build()).addConverterFactory(GsonConverterFactory.create()).build()
            }

            return  INSTANCE
        }
    }

    //SEM USAR GENERICS
//    fun createCepService(): CepServices {
//        return getRetrofitInstance().create(CepServices::class.java)
//    }
    //USANDO GENERICS
    fun <S> createService(classe: Class<S>): S {
        return getRetrofitInstance().create(classe)
    }

}