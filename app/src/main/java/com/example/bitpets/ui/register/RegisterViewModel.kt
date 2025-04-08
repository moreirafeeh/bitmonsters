package com.example.bitpets.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.provider.MediaStore
import android.util.Base64
import com.example.bitpets.models.CepEntity
import com.example.bitpets.repository.RegisterRepository
import com.example.bitpets.services.CepServices
import com.example.bitpets.services.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.Exception

class RegisterViewModel(application : Application): AndroidViewModel(application) {

    var CepService =  RetroFitClient().createService(CepServices::class.java)

    var username = MutableLiveData<String>()

    var password = MutableLiveData<String>()

    var phone = MutableLiveData<String>()

    var email = MutableLiveData<String>()

    var cep = MutableLiveData<String>()

    var street = MutableLiveData<String>()

    var bairro = MutableLiveData<String>()

    var numero = MutableLiveData<String>()

    var Dtnascimento = MutableLiveData<String>()


    var registerData = MutableLiveData<RegisterData>()
    var registerData_: LiveData<RegisterData> = registerData

    private val repository = RegisterRepository.getInstance(application)

    init {

        username.value = ""

    }


//    fun validateData(param: String): Boolean {
//        return isFieldValid(param)
//
//    }

    fun register(context: Context,ID: String ,username: String, password: String, phone: String, email: String ) {

          registerData.value = RegisterData(ID, username,password,phone,email)

    }

    // MODELO ROOM------------------
    fun saveRegister(username: String, password: String, phone: String, email: String, cep: String, neighborhood: String, street: String, number: String, dtNascimento: String, ftPerfil: String){

        repository.cadastrar(username, password, phone, email, cep, neighborhood, street, number, dtNascimento, ftPerfil)


    }

    //---------------------PRECISA FAZER ROOM------------------
//    fun saveRegister(username: String, password: String, phone: String, email: String){
//
//        repository.cadastrar(username, password, phone, email)
//
//
//    }

    fun verifyCep(registerViewModel: RegisterViewModel, it: String){

        if(registerViewModel.isFieldValid(it, ValidationType.CEP)){

            var call: Call<CepEntity> = CepService.getCep(it)
            call.enqueue(object: Callback<CepEntity> {
                override fun onResponse(call: Call<CepEntity>, response: Response<CepEntity>) {
                    var infoCEP = response.body()
                    registerViewModel.bairro.value = infoCEP?.bairro
                    registerViewModel.street.value = infoCEP?.logradouro

                }

                override fun onFailure(call: Call<CepEntity>, t: Throwable) {
                    var s = "problema"
                }

            })
        }
    }

    fun treatImage(context: Context, data: Intent?, imageBitmapRounded: Bitmap?): String {

            //---------------Preparandop imagem para coloca no banco---
            val baos = ByteArrayOutputStream()
            imageBitmapRounded?.compress(Bitmap.CompressFormat.JPEG, 100, baos) // bm is the bitmap object
            val byteArrayImageBitMapRounded: ByteArray = baos.toByteArray()

            val encodedImage: String = Base64.encodeToString(byteArrayImageBitMapRounded, Base64.DEFAULT)

            return encodedImage

    }

    fun getRoundedImage(context: Context, data: Intent?): Bitmap?{
        try{
            val bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data?.data)
            var imageBitmapRounded = getCroppedBitmap(bitmap, 100)

            return imageBitmapRounded
        }
        catch (e: Exception){
            e.toString()
            throw e
        }

    }

    fun getCroppedBitmap(bmp: Bitmap, radius: Int): Bitmap? {
        val sbmp: Bitmap
        // Ajustamos o tamanho, se necessario
        sbmp = if (bmp.width != radius || bmp.height != radius) Bitmap.createScaledBitmap(
            bmp,
            radius,
            radius,
            false
        ) else bmp
        // Nova Imagem...
        val output = Bitmap.createBitmap(sbmp.width, sbmp.height, Bitmap.Config.ARGB_8888)
        // Canvas onde iremos desenhar
        val canvas = Canvas(output)
        //  Configuramos o Paint...
        val paint = Paint()
        val rect = Rect(0, 0, sbmp.width, sbmp.height)
        paint.setAntiAlias(true)
        paint.setFilterBitmap(true)
        paint.setDither(true)
        canvas.drawCircle(
            sbmp.width / 2 + 0.7f,
            sbmp.height / 2 + 0.7f,
            sbmp.width / 2 + 0.1f,
            paint
        )
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(sbmp, rect, rect, paint)
        return output
    }

    fun verifyAlltoEnableRegisterButton(): Boolean {

        if(
            isFieldValid(username.value.toString(), ValidationType.NOT_EMPTY) &&
            isFieldValid(password.value.toString(), ValidationType.NOT_EMPTY) &&
            isFieldValid(phone.value.toString(), ValidationType.PHONE) &&
            isFieldValid(email.value.toString(), ValidationType.EMAIL) &&
            isFieldValid(cep.value.toString(), ValidationType.CEP) &&
            isFieldValid(street.value.toString(), ValidationType.NOT_EMPTY) &&
            isFieldValid(bairro.value.toString(), ValidationType.NOT_EMPTY) &&
            isFieldValid(numero.value.toString(), ValidationType.NOT_EMPTY) //&&
            //isFieldValid(Dtnascimento.value.toString())

        ){

        return true

        }
        return false
    }


    fun isFieldValid(field: String, typeValidation: ValidationType): Boolean {

        return when(typeValidation){

            ValidationType.NORMAL -> field.length > 5

            ValidationType.EMAIL -> field.contains("@")

            ValidationType.PHONE -> field.length >= 11 && !field.contains("#")

            ValidationType.CEP -> field.length == 9

            ValidationType.NOT_EMPTY -> field.isNotEmpty()

            ValidationType.IMAGE -> field.isNotEmpty()

        }

    }


}