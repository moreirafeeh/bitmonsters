package com.example.bitpets.services

import com.example.bitpets.models.CepEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CepServices {

    @GET("ws/{cepNumber}/json")
    fun getCep(@Path("cepNumber") cepNumber: String?): Call<CepEntity>

   // @GET("ws")
    //fun getCep(@Query(value = "", encoded = true) cepNumber: String?): Call<CepEntity>

}