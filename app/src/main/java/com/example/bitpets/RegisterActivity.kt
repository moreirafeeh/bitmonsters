package com.example.bitpets

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import androidx.lifecycle.Observer

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.*
import com.example.bitpets.databinding.ActivityRegisterBinding

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap

import androidx.lifecycle.ViewModelProvider
import com.example.bitpets.ui.register.*
import com.santalu.maskara.widget.MaskEditText


class RegisterActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {


    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    var registerData: RegisterData = RegisterData( "" ,"","","","")
    var dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")

    companion object{

        private val PERMISSION_CODE = 1000
        private val IMAGE_CAPTURE_CODE = 1001
        private val SELECT_IMAGE = 1002
        private var ToastMonitoring = 0
    }



    var vFilename: String = ""
    private var selectedImage = ""

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //var CepService =  RetroFitClient().createService(CepServices::class.java)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.BtnRegister.isEnabled = false
        binding.BtnRegister.setBackgroundColor(resources.getColor(R.color.grey))
        binding.BtnRegister.setTextColor(resources.getColor(R.color.white))

        registerViewModel.username.observe(this, Observer{
            binding.username.setText(it)

        })

        registerViewModel.registerData_.observe(this, Observer{
            if (it != null) {
                registerData = it
            }
        })

        registerViewModel.phone.observe(this, Observer{
            binding.phone.setText(it)
        })

        registerViewModel.password.observe(this, Observer{
            binding.password.setText(it)
        })

        registerViewModel.email.observe(this, Observer{
            binding.email.setText(it)
        })

        registerViewModel.cep.observe(this, Observer{
            binding.cep.setText(it)
        })

        registerViewModel.bairro.observe(this, Observer{
            binding.neighborhood.setText(it)
        })

        registerViewModel.street.observe(this, Observer{
            binding.street.setText(it)
        })

        registerViewModel.numero.observe(this, Observer{
            binding.number.setText(it)
        })

        registerViewModel.Dtnascimento.observe(this, Observer{
            binding.DtNasc.setText(it)
        })



        binding.username.afterTextChanged {
            when(registerViewModel.isFieldValid( it, ValidationType.NORMAL ) && it.isNotEmpty()){

               true  -> changeColorEdit(binding.username, true)

               false -> changeColorEdit(binding.username, false)
            }

        }

        binding.password.afterTextChanged {
            when(registerViewModel.isFieldValid( it, ValidationType.NORMAL) && it.isNotEmpty()){

                true  -> changeColorEdit(binding.password, true)

                false -> changeColorEdit(binding.password, false)

            }

        }

        binding.phone.afterTextChanged {

            when(registerViewModel.isFieldValid( it, ValidationType.PHONE) && it.isNotEmpty()){

                true -> changeColorEdit(binding.phone, true)

                false -> changeColorEdit(binding.phone, false)

            }



        }
        binding.email.afterTextChanged {

            when(registerViewModel.isFieldValid( it, ValidationType.EMAIL ) && it.isNotEmpty()){
                true  -> changeColorEdit(binding.email, true)

                false -> changeColorEdit(binding.email, false)
            }

        }

        binding.cep.afterTextChanged {
            when(registerViewModel.isFieldValid(it, ValidationType.CEP) && it.isNotEmpty()){


                true ->  {changeColorEdit(binding.cep, true)
                          registerViewModel.verifyCep(registerViewModel, it)
                          }

                false -> changeColorEdit(binding.cep, false)
            }



        }

        binding.neighborhood.afterTextChanged {
            when(registerViewModel.isFieldValid( it, ValidationType.NORMAL ) && it.isNotEmpty()){

                true  -> changeColorEdit(binding.neighborhood, true)

                false -> changeColorEdit(binding.neighborhood, false)

            }

        }

        binding.street.afterTextChanged {

            when(registerViewModel.isFieldValid( it, ValidationType.NORMAL ) && it.isNotEmpty()){

                true -> changeColorEdit(binding.street, true)

                false-> changeColorEdit(binding.street, false)

            }


        }

        binding.number.afterTextChanged {

            when(registerViewModel.isFieldValid( it, ValidationType.NOT_EMPTY) && it.isNotEmpty()){

                true ->  changeColorEdit(binding.number, true)

                false ->  changeColorEdit(binding.number, false)

            }

        }



        binding.BtnRegister.setOnClickListener(this)
        binding.DtNasc.setOnClickListener(this)
        binding.photo.setOnClickListener(this)


    }



    override fun onClick(view: View?) {
        if(view?.id == binding.BtnRegister.id){
            if(verifyAlltoEnableRegisterButton()){

                //MODELO ROOMM----------------------------
                registerViewModel.saveRegister(
                    binding.username.text.toString(),
                    binding.password.text.toString(),
                    binding.phone.text.toString(),
                    binding.email.text.toString(),
                    binding.cep.text.toString(),
                    binding.neighborhood.text.toString(),
                    binding.street.text.toString(),
                    binding.number.text.toString(),
                    binding.DtNasc.text.toString(),
                    selectedImage.toString()

                )

                //MODELO Query
                //registerViewModel.saveRegister(binding.username.text.toString(), binding.password.text.toString(), binding.phone.text.toString(), binding.email.text.toString())

                Toast.makeText(this, "Dados Salvos", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Dados Incorretos", Toast.LENGTH_SHORT).show()
            }

        }
        else if(view?.id == binding.photo.id){
            //Toast.makeText(this, "CLICAVEL", Toast.LENGTH_SHORT).show()
            //openCamera()
            //selectGallery()
            //abrirDialog()
            customAbrirDialog()
        }
        else if(view?.id == binding.DtNasc.id){
            handleDate()
        }
    }

    override fun onDateSet(v: DatePicker?, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val dueDate = dateFormat.format(calendar.time)
        binding.DtNasc.setText(dueDate)


    }

    fun handleDate(){
        val calendar: Calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()
    }


    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun MaskEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {

                afterTextChanged.invoke(editable.toString())

                binding.BtnRegister.isEnabled = verifyAlltoEnableRegisterButton()

                when(binding.BtnRegister.isEnabled){

                    true -> { binding.BtnRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.teal_200))
                              binding.BtnRegister.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))
                            }

                    false -> { binding.BtnRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))
                                binding.BtnRegister.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))
                           }

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {

                afterTextChanged.invoke(editable.toString())

                binding.BtnRegister.isEnabled = verifyAlltoEnableRegisterButton()

                when(binding.BtnRegister.isEnabled){

                    true -> { binding.BtnRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.teal_200))
                        binding.BtnRegister.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))
                    }

                    false -> { binding.BtnRegister.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))
                        binding.BtnRegister.setTextColor(ColorStateList.valueOf(resources.getColor(R.color.black)))
                    }

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }


    private fun verifyAlltoEnableRegisterButton(): Boolean{

         if(registerViewModel.isFieldValid(selectedImage, ValidationType.NOT_EMPTY)) {
                return  registerViewModel.isFieldValid(binding.username.text.toString(), ValidationType.NOT_EMPTY) &&
                        registerViewModel.isFieldValid(binding.password.text.toString(), ValidationType.NOT_EMPTY) &&
                        registerViewModel.isFieldValid(binding.phone.text.toString(), ValidationType.PHONE) &&
                        registerViewModel.isFieldValid(binding.email.text.toString(), ValidationType.EMAIL) &&
                        registerViewModel.isFieldValid(binding.cep.text.toString(), ValidationType.CEP) &&
                        registerViewModel.isFieldValid(binding.street.text.toString(), ValidationType.NOT_EMPTY) &&
                        registerViewModel.isFieldValid(binding.neighborhood.text.toString(), ValidationType.NOT_EMPTY) &&
                        registerViewModel.isFieldValid(binding.number.text.toString(), ValidationType.NOT_EMPTY) &&
                        registerViewModel.isFieldValid(binding.DtNasc.text.toString(), ValidationType.NOT_EMPTY)

         }
         else{

             if(ToastMonitoring >= 1){
                 Toast.makeText(this, "Insira uma foto de Perfil", Toast.LENGTH_SHORT).show()
                 binding.tvPhotoPerfil.setBackgroundColor(resources.getColor(R.color.red))
             }

             ToastMonitoring++

             return false
         }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // set filename
        //val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        //vFilename = "FOTO_" + timeStamp + ".jpg"

        // set direcory folder
        //val file = File("/sdcard/DCIM/Camera", vFilename);
        //val image_uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);

        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
        //startActivityForResult(Intent.createChooser(intent, "Escolha sua Imagem"), IMAGE_CAPTURE_CODE)
    }

    private fun selectGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                } else{
                    //permission from popup was denied
                    Toast.makeText(this, "NEGADA", Toast.LENGTH_SHORT).show()

                }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE){
            if (resultCode == RESULT_OK) {

                var imageBitmapRounded = registerViewModel.getRoundedImage(this, data)
                binding.photo.setImageBitmap(imageBitmapRounded)

                //---------------Preparandop imagem para coloca no banco---
                selectedImage = registerViewModel.treatImage(this, data, imageBitmapRounded)
                binding.tvPhotoPerfil.setBackgroundColor(resources.getColor(R.color.trans))

            }
        }
        else if(requestCode == IMAGE_CAPTURE_CODE ){
            if (resultCode == RESULT_OK) {

                //File object of camera image
                var imageBitmap = data?.extras?.get("data") as Bitmap

                //val file = File("/sdcard/DCIM/Camera", vFilename);
                //Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()

                //Uri of camera image
                //val uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                var imageBitmapRounded = registerViewModel.getCroppedBitmap(imageBitmap, 100)
                binding.photo.setImageBitmap(imageBitmapRounded)

                selectedImage = registerViewModel.treatImage(this, data, imageBitmapRounded)
                binding.tvPhotoPerfil.setBackgroundColor(resources.getColor(R.color.trans))


            }


        }

    }


    fun customAbrirDialog(){

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnPhoto: ImageView = dialog.findViewById(R.id.Photo)
        val btnGallery: ImageView = dialog.findViewById(R.id.Gallery)
        val btnCancel: Button = dialog.findViewById(R.id.cancelar)

        btnPhoto.setOnClickListener{
            openCamera()
            dialog.dismiss()
        }
        btnGallery.setOnClickListener{
            selectGallery()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()

    }

//=-------FAZER REUTILIZACAO DE BIDINGS DE COR------
    private fun changeColorEdit(type: EditText, color: Boolean){

    when(color){

        true -> {
                  type.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.teal_200))
                  type.setHintTextColor(ColorStateList.valueOf(resources.getColor(R.color.teal_200)))
                    }
        false -> {
                 type.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
                 type.setHintTextColor(ColorStateList.valueOf(resources.getColor(R.color.red)))
        }
    }


    }

}