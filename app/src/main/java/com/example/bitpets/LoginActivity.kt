package com.example.bitpets


import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bitpets.databinding.ActivityMainBinding
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.USUARIO
import com.example.bitpets.ui.ChoosePet.ChoosePetActivity
import com.example.bitpets.ui.login.LoginFormViewModel

import com.example.bitpets.ui.register.RegisterData

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    var registerData = RegisterData( "" , "",  "",  "",  "")
    var registerDataList: List<RegisterData>? = null
    lateinit var viewModel: LoginFormViewModel
    lateinit var user: USUARIO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginFormViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtLogin.setOnClickListener(this)
        binding.register.setOnClickListener(this)

        supportActionBar?.hide()

//        var info = SecurityPreferences(this).getPetInfo("Register")
//        if(info != ""){
//            registerDataList = Gson().fromJson(info, Array<RegisterData>::class.java).toList()
//            VerifyUserName()
//        }


    }


    override fun onClick(view: View) {

        if(view.id == binding.BtLogin.id){

            user = viewModel.getinfoUser(binding.userName.text.toString(), binding.Password.text.toString())

            if( user.NAME != "" ){
                //SecurityPreferences(this).storeString("UserName", binding.userName.text.toString())
                if(VerifyHasPet() == 1){
                    startActivity(Intent(this, PetRoomActivity::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this, ChoosePetActivity::class.java))
                    finish()
                }

            }
            else{
                Toast.makeText(this, "Usuario ou Senha incorretos", Toast.LENGTH_SHORT).show()
            }

        }
        else if(view.id == binding.register.id){
            Toast.makeText(this, "cliquei", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

//    fun VerifyUserName(){
//        if(verifyUserNameAndPassword()){
//
//            if(VerifyHasPet() == 1){
//                startActivity(Intent(this, PetRoomActivity::class.java))
//                finish()
//            }
//            else{
//                startActivity(Intent(this, ChoosePetActivity::class.java))
//                finish()
//            }
//
//        }
//
//    }

    private fun VerifyHasPet(): Int {

        var petInfo: PET = viewModel.getinfoPet(user.ID_USER)

        if( petInfo.PET_NAME != "") {

            //Toast.makeText(this, "USUARIO JA TEM PET", Toast.LENGTH_SHORT).show()
            return 1;
        }
        else{
            return 0
        }
    }

//    private fun verifyUserNameAndPassword(): Boolean {
//        for ( i in registerDataList!!){
//            if(i.usuario == binding.userName.text.toString() && i.password == binding.Password.text.toString()){
//                LoginServices().setId(i.usuario)
//                return true
//            }
//        }
//        return false
//    }





}