package com.example.bitpets.ui.ChoosePetSelector

import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitpets.databinding.ActivityChoosePetSelectorBinding
import android.media.MediaPlayer

import android.widget.Toast
import com.example.bitpets.PetRoomActivity
import com.example.bitpets.R
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.room.entities.HABILIDADES_PET
import com.example.bitpets.ui.login.LoginFormViewModel


class ChoosePetSelectorActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityChoosePetSelectorBinding
    lateinit var viewModel: ChoosePetSelectorViewModel

    private val cars = arrayOf(
        R.drawable.poring,
        R.drawable.green_poring,
        R.drawable.bombring,
        R.drawable.succubus
    )
    private val petName = arrayOf("poring" , "green_poring", "bombring","succubus")
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoosePetSelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChoosePetSelectorViewModel::class.java)

        binding.BtnLeft.setOnClickListener(this)
        binding.BtnRight.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        supportActionBar?.hide()
        MusicSelectorScreen(R.raw.streamside)
        //Glide.with(this).load(R.drawable.poring).asGif().into(binding.poring)

    }

    override fun onClick(view: View) {


        if(view.id == binding.BtnLeft.id){
            MusicSelectorScreen(R.raw.shooting)
            i--

            if(i == -1){
                print("----------------------------")
                print(cars.size)
                i = cars.size - 1
                binding.poring.setImageResource(cars[i])
                binding.BitPetName.setText(petName[i])
            }
            else{
                binding.poring.setImageResource(cars[i])
                binding.BitPetName.setText(petName[i])
            }
        }

        else if(view.id == binding.BtnRight.id){
            MusicSelectorScreen(R.raw.shooting)
            i++

            if(i >= cars.size){
                i = 0
                binding.poring.setImageResource(cars[i])
                binding.BitPetName.setText(petName[i])
            }
            else{
                binding.poring.setImageResource(cars[i])
                binding.BitPetName.setText(petName[i])
            }
        }

        else if(view.id == binding.btnNext.id){


                var name = binding.name.text.toString()
                var pet_type =  binding.BitPetName.text.toString()
                var Life = binding.vida.text.toString().toInt()
                var Attack = binding.ataque.text.toString().toInt()
                var Defense = binding.defesa.text.toString().toInt()
                var Sede = binding.sede.text.toString().toInt()
                var Hungry = binding.fome.text.toString().toInt()


                viewModel.savePet(this, name, pet_type, Life ,Attack, Defense, Sede, Hungry )
                var pet = viewModel.getPetUser(this)
                var hp = HABILIDADES_PET().apply {
                    this.ID_PET = pet.ID_PET
                    this.ID_HABILIDADE = 2
                }
                viewModel.saveHabilities(hp)

                Toast.makeText(this, "BitPet Criado com Sucesso", Toast.LENGTH_SHORT).show()

//
//            SecurityPreferences(this).storePetInfo("PET", """{"NOME": "$name" , "VIDA": $Life , "ATAQUE": $Attack , "DEFESA": $Defense , "SEDE": $Sede , "FOME": $Hungry, "pet": $pet }""")
//            Toast.makeText(this, "SALVO", Toast.LENGTH_SHORT).show()
//
            startActivity(Intent(this, PetRoomActivity::class.java))
            finish()

        }



    }

    fun MusicSelectorScreen(music: Int){
        val mp: MediaPlayer = MediaPlayer.create(this, music)
        mp.setOnCompletionListener { mp -> mp.release() }
        mp.start()
    }





}