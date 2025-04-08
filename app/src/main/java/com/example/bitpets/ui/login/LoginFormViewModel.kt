package com.example.bitpets.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.models.LoginModel
import com.example.bitpets.models.PetModel
import com.example.bitpets.repository.LoginRepository
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO
import java.lang.Exception

class LoginFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoginRepository(application.applicationContext)

    //-----------------MODO SEM ROOM------
//    //VOLTAR PRA TERMINAR QUE FALTA VARIAS COISAS!!!
//    fun verifyUser(usuario: String, senha: String): Boolean {
//        return true
//    }
//    fun getinfoUser(usuario: String, senha: String): Boolean{
//
//
//        var loginModel: LoginModel = repository.getParticularUserData(usuario, senha)
//
//        if(loginModel.ID_USER != 0){
//            SecurityPreferences(getApplication()).storeUserID(loginModel.ID_USER.toString())
//            return true
//        }
//        else{
//            return false
//        }
//
//        return false
//
//    }
//
//    fun getinfoPet(userID: String): Boolean{
//
//        var petModel: PetModel = repository.getPetInfo(userID)
//
//        petModel
//
//        return petModel.ID_USER != 0
//
//
//    }



    fun getinfoUser(usuario: String, senha: String): USUARIO{

        var loginModel: USUARIO = repository.getuserInfo(usuario, senha)

        if(loginModel != null){

                SecurityPreferences(getApplication()).storeUserID(loginModel.ID_USER.toString())
        }
        else{
            loginModel = USUARIO()
        }

        return loginModel


    }

    fun getinfoPet(idUser: Int): PET{

        var loginModel: PET = repository.getPetInfo(idUser)

        if(loginModel == null){
            loginModel = PET()
        }

        return loginModel
    }


}