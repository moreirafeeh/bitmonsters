package com.example.bitpets.ui.dashboard

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.os.Handler
import com.example.bitpets.databinding.FragmentDashboardBinding

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.example.bitpets.Monstros.Monstros
import com.example.bitpets.R
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.ui.home.FoodImage
import com.example.bitpets.ui.home.PetImage
import com.example.bitpets.ui.listener.CardItemListener


class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var reciclerview: RecyclerView

    private var _binding: FragmentDashboardBinding? = null
    //private lateinit var pet: PET
    private val binding get() = _binding!!
    var attackController: Int = 0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        recyclerViewvHandle()

        val adapter = CardItemAdapter(dashboardViewModel.cardItemList)
        reciclerview.adapter = adapter

//        dashboardViewModel._updateAdapter.observe(this, Observer{
//            reciclerview.adapter = it
//        })

        dashboardViewModel.bag.observe(viewLifecycleOwner, Observer {


        })


        dashboardViewModel.petLifeBarVillainMax.observe(viewLifecycleOwner, Observer {
            binding.PetLifeBarvillain.max = it
        })

        dashboardViewModel.inimigo.value!!.vida.observe(viewLifecycleOwner, Observer {

            binding.PetLifeBarvillain.progress = it
            binding.lifePointsVillain.text = it.toString()

        })

        dashboardViewModel.inimigo.value!!.image.observe(viewLifecycleOwner, Observer {

            binding.villain.setImageResource(dashboardViewModel.inimigo.value!!.image.value!!)

        })


        dashboardViewModel.VillainLifePointsObserver.observe(viewLifecycleOwner, Observer {

            binding.PetLifeBarvillain.progress = it

        })


        //-----------------PET---------------

        dashboardViewModel.petMutable.value!!.VIDA.observe(viewLifecycleOwner, Observer {

            binding.PetLifeBar.progress = it
            binding.lifePoints.text = it.toString()

        })

        dashboardViewModel.PetLifeBarMaxObserver.observe(viewLifecycleOwner, Observer {
            binding.PetLifeBar.max = it
        })


        dashboardViewModel.MaxLifePoint.observe(viewLifecycleOwner, Observer{
            binding.lifePointsMax.text = it
        })

        //-------------------------------


        dashboardViewModel.textViewAcao.observe(viewLifecycleOwner, Observer {
            binding.tvAcao.text = it.toString()
        })


        //VENDO SE FUNCIONA-------------------------
        dashboardViewModel.petMutable.observe(viewLifecycleOwner, Observer {
            binding.tvPetName.text = it.PET_NAME
            binding.lifePoints.text = it.VIDA.value!!.toString()
        }
        )


        dashboardViewModel.MaxLifePointsVillain.observe(viewLifecycleOwner, Observer{
            binding.maxLifePointsVillain.text = it
        })

        dashboardViewModel.petHabilities.observe(viewLifecycleOwner, Observer {

            binding.attack1.text = it.NOME_HABILIDADE

        })

        dashboardViewModel.textLayoutMessage.observe(viewLifecycleOwner, Observer {

            binding.textLayout.isClickable = it

        })



//        dashboardViewModel.VillainLifePointsObserver.observe(viewLifecycleOwner, Observer {
//            binding.lifePointsVillain.text = it
//        })


        val listener =  object : CardItemListener{

            override fun Onclick(cardItem: CardItem) {

                if(dashboardViewModel.bag.value?.itemName == ""){

                    //inserir foto
                    binding.equipeditem.setImageResource(find_Item_Image(cardItem.itemID))
                    binding.equipeditemPet.setImageResource(find_Item_Image(cardItem.itemID))

                    //armazenando em um objeto
                    equipedItemBag(cardItem)

                    dashboardViewModel.cardItemList.removeAll {

                        it.itemID == cardItem.itemID

                    }

                    //restartando a  recyclerView com a Lista NOVA
                    reciclerview.adapter = adapter

                }
                else{

                    var cardItemChange: CardItem = CardItem(dashboardViewModel.bag.value!!.itemName , dashboardViewModel.bag.value!!.itemImage, dashboardViewModel.bag.value!!.itemID, dashboardViewModel.bag.value!!.ataque, dashboardViewModel.bag.value!!.description)
                    dashboardViewModel.cardItemList.add(cardItemChange)
                    //inserir foto

                    binding.equipeditem.setImageResource(find_Item_Image(cardItem.itemID))
                    binding.equipeditemPet.setImageResource(find_Item_Image(cardItem.itemID))

                    //armazenando em um objeto
                    equipedItemBag(cardItem)

                    dashboardViewModel.cardItemList.removeAll {

                        it.itemID == cardItem.itemID


                    }

                    //restartandoa  recyclerView com a Lista NOVA
                    reciclerview.adapter = adapter
                }


                //Toast.makeText(context, itemID, Toast.LENGTH_SHORT).show()
            }

            //NAO SERÀ USADO NO JOGO, POREM, SERVIU PARA ESTUDOS
            override fun OnDelete(itemID: Int) {

                dashboardViewModel.cardItemList.removeAll {

                        it.itemID == itemID
                    }

                //restartandoa  recyclerView com a Lista NOVA
                reciclerview.adapter = adapter

            }
            //-----------------------------------------------------

        }

        adapter.attachListener(listener)

        //binding.Btnattack.setOnClickListener(this)
        binding.equipeditem.setOnClickListener(this)
        binding.bag.setOnClickListener(this)
        binding.fecharModal.setOnClickListener(this)
        binding.fight.setOnClickListener(this)
        binding.backMenuDefault.setOnClickListener(this)
        binding.attack1.setOnClickListener(this)
        binding.textLayout.setOnClickListener(this)
        binding.dropItem.setOnClickListener(this)
        //PET-------------------
        //--------------PRECISA FAZER ROOM----------
        dashboardViewModel.getinfoPet(SecurityPreferences(requireActivity()).getPetInfo("USER_ID"))
        dashboardViewModel.getHabilities(dashboardViewModel.pet.value!!.ID_PET)

        dashboardViewModel.settLifePoint()
        dashboardViewModel.settVillainLifePoint()

        petImageSelect(dashboardViewModel.pet.value!!.TYPE_PET)

        //firstInitViewCombat()

        return root
    }


    fun firstInitViewCombat(){

        //binding.villain.setImageResource(dashboardViewModel.inimigo.value!!.image.value!!)


    }

    fun recyclerViewvHandle(){
        val rv = binding.rv
        rv.layoutManager =
            LinearLayoutManager(context)
        reciclerview = rv

    }


    fun attack(){

        binding.textLayout.isClickable = false
        binding.menuAttack.visibility = View.GONE

        dashboardViewModel.writeTextCharToChar("PORING UTILIZA CABEÇADA EM SUCCUBUS!")
        //binding.menuPet.visibility = View.GONE
        Handler().postDelayed(this::petAttack, 6000)


        Handler().postDelayed(this::attack_math, 9000)


        //Handler().postDelayed(this::changeMonster, 7000)

        //Handler().postDelayed(this::enemyAttack, 3000)
        //Handler().postDelayed(this::disableButton, 5000)


    }

    fun attackVillain(){

        binding.textLayout.isClickable = false
        binding.menuAttack.visibility = View.GONE

        dashboardViewModel.writeTextCharToChar("SUCCUBUS UTILIZA CABEÇADA")
        //binding.menuPet.visibility = View.GONE
        Handler().postDelayed(this::VillainAttack, 6000)
        binding.menuAttack.visibility = View.GONE

        Handler().postDelayed(this::attackMathVillain, 9000)


    }

    fun attackMathVillain(){

        var damage = (dashboardViewModel.inimigo.value!!.ataque.value!!)
        dashboardViewModel.reduceLifePointsPet(damage)
        //dashboardViewModel.inimigo.value!!.vida = dashboardViewModel.inimigo.value!!.vida - (dashboardViewModel.pet.value!!.ATAQUE + dashboardViewModel.bag.value!!.ataque)
        //binding.PetLifeBar.progress = dashboardViewModel.petMutable.value!!.VIDA.value!!
        dashboardViewModel.textLayoutMessage.value = true

    }

    fun changeMonster(){
        dashboardViewModel.change_monster()
    }

    fun disableButton(){
        //binding.Btnattack.isEnabled = true
    }

    fun attack_math(){
        var damage = (dashboardViewModel.pet.value!!.ATAQUE + dashboardViewModel.bag.value!!.ataque)
        dashboardViewModel.reduceLifePointVillain(damage)
        //dashboardViewModel.inimigo.value!!.vida = dashboardViewModel.inimigo.value!!.vida - (dashboardViewModel.pet.value!!.ATAQUE + dashboardViewModel.bag.value!!.ataque)
        //binding.PetLifeBarvillain.progress = dashboardViewModel.inimigo.value!!.vida.value!!
        dashboardViewModel.textLayoutMessage.value = true

    }

    fun petAttack(){

        //var attack_back_initial_position: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.pet_attack_initial_position);

        Handler().postDelayed(this::animAttack1, 0)
        Handler().postDelayed(this::animAttack2, 100)
        Handler().postDelayed(this::animAttack1, 200)
        Handler().postDelayed(this::animAttack2, 300)
        Handler().postDelayed(this::animAttack1, 400)
        Handler().postDelayed(this::attackPower, 1000)


        //binding.YourPet.startAnimation(attack_back_initial_position)
    }

    fun VillainAttack(){

        Handler().postDelayed(this::animAttack1Villain, 0)
        Handler().postDelayed(this::animAttack2Villain, 100)
        Handler().postDelayed(this::animAttack1Villain, 200)
        Handler().postDelayed(this::animAttack2Villain, 300)
        Handler().postDelayed(this::animAttack1Villain, 400)
        Handler().postDelayed(this::attackPowerVillain, 1000)

    }


    fun animAttack1(){
        var attack: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.pet_attack);
        binding.YourPet.startAnimation(attack)
    }
    fun animAttack2(){
        var attack_hit: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.pet_attack_hit);
        binding.YourPet.startAnimation(attack_hit)

    }

    fun animAttack1Villain(){
        var attack: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.pet_attack);
        binding.villain.startAnimation(attack)
    }
    fun animAttack2Villain(){
        var attack_hit: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.pet_attack_hit);
        binding.villain.startAnimation(attack_hit)

    }

    fun attackPowerVillain(){
        binding.powerAttackVillain.visibility = View.VISIBLE
        Handler().postDelayed(this::stopGifVillain, 2000)
    }

    fun attackPower(){
        binding.powerAttack.visibility = View.VISIBLE
        Handler().postDelayed(this::stopGif, 1000)
    }
    fun stopGif(){
        binding.powerAttack.visibility = View.GONE
    }

    fun stopGifVillain(){
        binding.powerAttackVillain.visibility = View.GONE
    }


    fun dropItem(){

    }

    fun find_Item_Image(ItemID: Int): Int {

        when(ItemID){
            1 -> return R.drawable.sword1
            2 -> return R.drawable.sword7
        }

        return 0

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
//        if(view.id == binding.Btnattack.id){
//            attack()
//        }
        if(view.id == binding.equipeditem.id){
                if(dashboardViewModel.bag.value!!.itemName != ""){
                    val cardItem: CardItem = CardItem(dashboardViewModel.bag.value!!.itemName , dashboardViewModel.bag.value!!.itemImage, dashboardViewModel.bag.value!!.itemID, dashboardViewModel.bag.value!!.ataque, dashboardViewModel.bag.value!!.description)
                    dashboardViewModel.cardItemList.add(cardItem)

                    binding.equipeditem.setImageResource(0)
                    binding.equipeditemPet.setImageResource(0)
                    //armazenando em um objeto

                    dashboardViewModel.bag.value = EquipedItem(0,"",0, 0, "")

                    reciclerview.adapter = reciclerview.adapter

                }

        }

        else if(view.id == binding.bag.id){
            binding.bagModal.visibility =  View.VISIBLE
        }
        else if(view.id == binding.fecharModal.id){
            binding.bagModal.visibility =  View.GONE
        }
        else if(view.id == binding.fight.id){
            binding.menuAttack.visibility =  View.VISIBLE
            binding.menuPet.visibility =  View.GONE
        }
        else if(view.id == binding.backMenuDefault.id){
            binding.menuAttack.visibility = View.GONE
            binding.menuPet.visibility =  View.VISIBLE
        }
        else if(view.id == binding.attack1.id){
            attack()
        }
        else if(view.id == binding.textLayout.id){

            attackController++
            if(attackController == 1 && dashboardViewModel.inimigo.value!!.vida.value!!.toInt() > 0){
                dashboardViewModel.textViewAcao.value = ""
                binding.menuAttack.visibility = View.VISIBLE
                dashboardViewModel.textLayoutMessage.value = false
                changeMonster()
            }
            else{
                if(dashboardViewModel.inimigo.value!!.vida.value!!.toInt() > 0){
                    attackVillain()
                }
                else{

                    binding.villain.visibility = View.INVISIBLE
                    binding.villain.setImageResource(0)
                    binding.dropItem.setImageResource(FoodImageSelect(dashboardViewModel.dropItens.get(0).ITEM_IMAGE.value!!))
                    dashboardViewModel.textLayoutMessage.value = false
                    binding.dropItem.isClickable = true
                }

                attackController = 0

            }

        }

        else if(view.id == binding.dropItem.id){
            changeMonster()
            dashboardViewModel.postPetItem(0)
            binding.villain.visibility = View.VISIBLE
            binding.dropItem.isClickable = false
            binding.dropItem.setImageResource(0)

            //
            dashboardViewModel.textViewAcao.value = ""
            binding.menuAttack.visibility = View.VISIBLE
            dashboardViewModel.textLayoutMessage.value = false


        }

//        else if(view.id == binding.dropItem2.id){
//            changeMonster()
//        }


    }

//    fun customAbrirDialog(){
//
//        val dialog = Dialog(requireActivity())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.dialog_box)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        //val btnPhoto: ImageView = dialog.findViewById(R.id.Photo)
//        //val btnGallery: ImageView = dialog.findViewById(R.id.YourPet)
//        val btnCancel: Button = dialog.findViewById(R.id.cancelar)
//
////        btnPhoto.setOnClickListener{
////            openCamera()
////            dialog.dismiss()
////        }
////        btnGallery.setOnClickListener{
////            selectGallery()
////            dialog.dismiss()
////        }
//        btnCancel.setOnClickListener{
//            dialog.dismiss()
//        }
//
//        dialog.show()
//
//    }

    fun equipedItemBag(cardItem: CardItem){

        dashboardViewModel.bag.value!!.itemName = cardItem.itemName
        dashboardViewModel.bag.value!!.itemID = cardItem.itemID
        dashboardViewModel.bag.value!!.itemImage =  cardItem.itemImage
        dashboardViewModel.bag.value!!.ataque =  cardItem.ataque
        dashboardViewModel.bag.value!!.description =  cardItem.description
    }

    private fun petImageSelect(pet: String){

        if(pet == PetImage.poring.toString()){
            binding.YourPet.setImageResource(R.drawable.poring)
        }
        else if(pet ==  PetImage.green_poring.toString()){
            binding.YourPet.setImageResource(R.drawable.green_poring)
        }
        else if(pet ==  PetImage.bombring.toString()){
            binding.YourPet.setImageResource(R.drawable.bombring)
        }
        else if(pet ==  PetImage.succubus.toString()){
            binding.YourPet.setImageResource(R.drawable.succubus)
        }
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



}