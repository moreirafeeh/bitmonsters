package com.example.bitpets

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {

    private val security: SharedPreferences = context.getSharedPreferences("Motivation", Context.MODE_PRIVATE)

    fun storeRegisterInfo(key: String, str: String){
        security.edit().putString(key, str).apply()
    }

    fun getRegisterInfo(key: String): String{
        return security.getString(key, "") ?: ""
    }

    fun storePetInfo(key: String, petName: String){
        security.edit().putString(key, petName).apply()
    }

    fun getPetInfo(key: String): String{
        return security.getString(key, "") ?: ""
    }

    fun storeUserID( value: String){
        security.edit().putString("USER_ID", value).apply()
    }

    fun getUserID(): String {
        return security.getString("USER_ID", "") ?: ""
    }



}