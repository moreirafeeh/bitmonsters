package com.example.bitpets.services

class LoginServices {

    var usernameLink: String = ""
    val ID_user: Int? = null

    fun VerifyAccess(name: String,  password: String): Boolean{
        if(name == "X" && password == "X"){
            return true
        }
        return false
    }




}