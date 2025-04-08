package com.example.bitpets.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.example.bitpets.PetRoomActivity
import com.example.bitpets.R
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.databinding.FragmentHomeBinding
import com.example.bitpets.models.PetModel

import com.google.gson.Gson
import java.lang.NullPointerException
import android.app.Activity
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.PET_ITENS

import kotlinx.datetime.*
import pl.droidsonroids.gif.GifImageView
import java.lang.Exception
import kotlin.time.Duration


class HomeFragment : Fragment(), Runnable, View.OnClickListener {

    private val handler: Handler = Handler()
    private var QTD_POOP: Int = 0

    //private var petModel: PET = PET()

    companion object{
        private lateinit var USER_ID: String
    }


    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    data class BitMonster(var NOME: String, var VIDA: Int, var ATAQUE: Int,  var DEFESA: Int, var SEDE: Int, var FOME: Int, var pet: String)


    var sup = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        USER_ID = SecurityPreferences(requireActivity()).getPetInfo("USER_ID")

        var gifController: ImageView = binding.poring

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //homeViewModel.text.observe(viewLifecycleOwner, Observer { textView.text = it })

        //TESTANDO MONITORAMENTO DE IMAGEVIEW
        homeViewModel.gifMutable.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                gifController = it
            }
        })

        homeViewModel.petModel.observe(requireActivity(), Observer {
            binding.money.text = it.BIT_MONSTER_COIN.toString()
            binding.determinateBar.progress = it.FOME
        })

        homeViewModel.petItens.observe(requireActivity(), Observer {


        })


        //-------------------------ROOM------

        homeViewModel.getinfoPet(USER_ID)

        homeViewModel.checkPoopHour()
        areTherePoop(homeViewModel.petModel.value!!.QTD_COCO)

        homeViewModel.updatePoopQuantity(USER_ID)
        homeViewModel.updateHour(USER_ID)
        //homeViewModel.updateInfoPet(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), )

        petImageSelect(homeViewModel.petModel.value!!.TYPE_PET)
        binding.petName.setText(homeViewModel.petModel.value!!.PET_NAME)
        //-----------------------------------
        //-------FOME-------
        homeViewModel.calculateHungry()
        homeViewModel.updateHungry(USER_ID)


        if(isAdded() && activity != null) {
            run()
        }

        //binding.money.text = petModel.BIT_MONSTER_COIN.toString()

        binding.poring.setOnClickListener(this)
        binding.poop1.setOnClickListener(this)
        binding.poop2.setOnClickListener(this)
        binding.poop3.setOnClickListener(this)
        binding.poop4.setOnClickListener(this)
        binding.poop5.setOnClickListener(this)
        binding.poop6.setOnClickListener(this)
        binding.bagIcon.setOnClickListener(this)
        binding.fecharModal.setOnClickListener(this)
        binding.itembox1.setOnClickListener(this)
        binding.itembox2.setOnClickListener(this)
        binding.itembox3.setOnClickListener(this)
        binding.itembox4.setOnClickListener(this)
        binding.itembox5.setOnClickListener(this)
        binding.itembox6.setOnClickListener(this)
        binding.itembox7.setOnClickListener(this)
        binding.itembox8.setOnClickListener(this)
        binding.itembox9.setOnClickListener(this)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FoodImageSelect(pet: String): Int{

        if(pet == FoodImage.SUSHI.toString()){
            return R.drawable.sushi
        }
        else if(pet == FoodImage.TOMATE.toString()){
            return R.drawable.tomate
        }
        return 0
    }

    private fun petImageSelect(food: String){

        if( food == PetImage.poring.toString()){
            binding.poring.setImageResource(R.drawable.poring)
        }
        else if(food ==  PetImage.green_poring.toString()){
            binding.poring.setImageResource(R.drawable.green_poring)
        }
        else if(food ==  PetImage.bombring.toString()){
            binding.poring.setImageResource(R.drawable.bombring)
        }
        else if(food ==  PetImage.succubus.toString()){
            binding.poring.setImageResource(R.drawable.succubus)
        }
    }


    override fun run() {

        val activity: Activity? = activity

        if(isAdded && activity != null) {

            handler.postDelayed(this, 5000)// 4 seconds

            // load the animation
            var animUp: Animation =
                AnimationUtils.loadAnimation(requireActivity(), R.anim.mover_objeto);
            var animDown: Animation =
                AnimationUtils.loadAnimation(requireActivity(), R.anim.mover_baixo);
            var animRight: Animation =
                AnimationUtils.loadAnimation(requireActivity(), R.anim.mover_direita);
            var animinitial: Animation =
                AnimationUtils.loadAnimation(requireActivity(), R.anim.animation_initial);

            // start the animation

            when (sup) {
                0 -> {
                    binding.poring.startAnimation(animUp)
                    binding.petName.startAnimation(animUp)
                    sup++
                }
                1 -> {
                    binding.poring.startAnimation(animDown)
                    binding.petName.startAnimation(animDown)
                    sup++
                }
                2 -> {
                    binding.poring.startAnimation(animRight)
                    binding.petName.startAnimation(animRight)
                    sup++
                }
                else -> {
                    binding.poring.startAnimation(animinitial)
                    binding.petName.startAnimation(animinitial)
                    sup = 0
                }
            }

        }



   }


    override fun onClick(view: View) {
        when(view.id){

            binding.poring.id -> binding.emoji.visibility = View.VISIBLE

            binding.poop1.id  -> {
                binding.poop1.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)

            }

            binding.poop2.id  -> {
                binding.poop2.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)


            }

            binding.poop3.id  -> {
                binding.poop3.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)

            }

            binding.poop4.id  -> {
                binding.poop4.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)


            }

            binding.poop5.id  -> {
                binding.poop5.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)


            }

            binding.poop6.id  -> {
                binding.poop6.visibility = View.GONE

                homeViewModel.updateReducePoopQuantity(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.updateMoney(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
                homeViewModel.getinfoPet(USER_ID)

            }

            binding.bagIcon.id  -> {
                binding.bagModal.visibility = View.VISIBLE
                homeViewModel.getPetItensInfo(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))

                homeViewModel.petItens.value!!.size

                updateBag()

            }

            binding.fecharModal.id -> {
                binding.bagModal.visibility = View.GONE
            }

            binding.itembox1.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 0)
                updateBag()
            }

            binding.itembox2.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 1)
                updateBag()
            }

            binding.itembox3.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 2)
                updateBag()
            }

            binding.itembox4.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 3)
                updateBag()
            }

            binding.itembox5.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 4)
                updateBag()
            }

            binding.itembox6.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 5)
                updateBag()
            }

            binding.itembox7.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 6)
                updateBag()
            }

            binding.itembox8.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 7)
                updateBag()
            }

            binding.itembox9.id -> {
                homeViewModel.useItem(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"), 8)
                updateBag()
            }




        }


    }

    fun updateBag(){

        binding.itembox1.setImageResource(0)
        binding.itembox2.setImageResource(0)
        binding.itembox3.setImageResource(0)
        binding.itembox4.setImageResource(0)
        binding.itembox5.setImageResource(0)
        binding.itembox6.setImageResource(0)
        binding.itembox7.setImageResource(0)
        binding.itembox8.setImageResource(0)
        binding.itembox9.setImageResource(0)


        try{
            binding.itembox1.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![0].ITEM_IMAGE.value.toString()))
            binding.itembox2.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![1].ITEM_IMAGE.value.toString()))
            binding.itembox3.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![2].ITEM_IMAGE.value.toString()))
            binding.itembox4.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![3].ITEM_IMAGE.value.toString()))
            binding.itembox5.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![4].ITEM_IMAGE.value.toString()))
            binding.itembox6.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![5].ITEM_IMAGE.value.toString()))
            binding.itembox7.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![6].ITEM_IMAGE.value.toString()))
            binding.itembox8.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![7].ITEM_IMAGE.value.toString()))
            binding.itembox9.setImageResource(FoodImageSelect(homeViewModel.petItens.value!![8].ITEM_IMAGE.value.toString()))
        }
        catch (e: Exception){
            println("preenchi até odne tinha lista")
        }

    }

    fun areTherePoop(qtdPoop: Int){

        var poopList: List<GifImageView> = listOf(
            binding.poop1,
            binding.poop2,
            binding.poop3,
            binding.poop4,
            binding.poop5,
            binding.poop6
        )

        for(i in poopList){

            if(poopList.indexOf(i) < qtdPoop){
                    i.visibility = View.VISIBLE
            }

        }


    }




}