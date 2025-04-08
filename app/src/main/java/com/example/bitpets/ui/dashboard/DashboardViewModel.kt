package com.example.bitpets.ui.dashboard

import android.app.Application
import android.widget.LinearLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bitpets.Monstros.Monstros
import com.example.bitpets.Monstros.Pet
import com.example.bitpets.Monstros.PetItens
import com.example.bitpets.R
import com.example.bitpets.SecurityPreferences
import com.example.bitpets.models.PetModel
import com.example.bitpets.repository.ChoosePetSelectorRepository
import com.example.bitpets.repository.DashboardRepository
import com.example.bitpets.repository.HomeFragmentRepository
import com.example.bitpets.room.entities.HABILIDADES
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.PET_ITENS
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application): AndroidViewModel(application) {

    private val repository = HomeFragmentRepository.getInstance(application)
    private val repositoryDashBoard = DashboardRepository.getInstance(application)



    var cardItemList = mutableListOf(CardItem("Espada de Ferro",R.drawable.sword1, 1, 900,"Dano Normal +30 se seu pet for do elemento Terra"),
                                     CardItem("Espada Gélida",  R.drawable.sword7, 2, 200,"acrescenta dano de gelo +20 caso seu pet seja um poring",))

    //Drop itens-----------------

    var listItens = listOf<PetItens>(PetItens(0, 0, MutableLiveData("FOOD"), MutableLiveData(100),   MutableLiveData(0),  MutableLiveData(0),  MutableLiveData("TOMATE")))

    var dropItens = ArrayList<PetItens>(listItens)

    //---------------------------
    var inimigo = MutableLiveData(Monstros("Succubus", MutableLiveData(1000),MutableLiveData(30), MutableLiveData(R.drawable.monster1)))

    var bag =  MutableLiveData(EquipedItem(0,"",0, 0, ""))

    val adapter = MutableLiveData<CardItemAdapter>()

    var textViewAcao = MutableLiveData<String>()

    var textoParaParticionar = ""

    var petHabilities = MutableLiveData<HABILIDADES>()

    var pet = MutableLiveData<PET>()



    //-------------INIMIGO-----------

    var MaxLifePointsVillain = MutableLiveData<String>()

    var VillainLifePointsObserver = MutableLiveData<Int>()

    var petLifeBarVillainMax = MutableLiveData<Int>()

    //-------------------------------


    var textLayoutMessage = MutableLiveData<Boolean>(false)

    var petMutable =  MutableLiveData<Pet>(Pet(0, 0 , "", "", MutableLiveData(100), MutableLiveData(100), MutableLiveData(100), MutableLiveData(100), MutableLiveData(100), "", 0 ,0))

    //--------------------------------PET----
    //Controle dos pontos do pet
    var PetLifePointsObserver = MutableLiveData<Int>()

    //Controle da BARRA maxima do pet
    var PetLifeBarMaxObserver =  MutableLiveData<Int>()

    //Controle de PONTOS maximos do pet
    var MaxLifePoint = MutableLiveData<String>()


    private val _text = MutableLiveData<String>().apply { value = "This is dashboard Fragment" }
    val text: LiveData<String> = _text

    private var updateAdapter = MutableLiveData<CardItemAdapter>()

    init{
        adapter.value = CardItemAdapter(cardItemList)
        updateAdapter = adapter


    }

    fun getinfoPet(userID: String) {

        pet.value = repository.getPetInfo(userID)

    }

    fun getHabilities(userID: Int) {

        petHabilities.value = repositoryDashBoard.getPetHabilitiesInfo(userID)

    }
    fun postPetItem(index: Int){

        var petItemEntity: PET_ITENS =  convertToPetItem(dropItens.get(index))
        repository.insert(petItemEntity)

    }

    fun convertToPetItem(petItens: PetItens): PET_ITENS{

        var petItemEntity: PET_ITENS = PET_ITENS()

        petItemEntity.ID_ITEM = petItens.ID_ITEM
        petItemEntity.ID_PET = pet.value!!.ID_PET
        petItemEntity.TYPE = petItens.TYPE.value!!
        petItemEntity.FOME = petItens.FOME.value!!
        petItemEntity.DANO = petItens.DANO.value!!
        petItemEntity.DEFESA = petItens.DEFESA.value!!
        petItemEntity.ITEM_IMAGE = petItens.ITEM_IMAGE.value!!

        return petItemEntity
    }


    fun writeTextCharToChar(texto: String) {

        val textbrincar = texto


        GlobalScope.launch {

            var genText = ""

            for(text in textbrincar){
                 genText += text
                textViewAcao.postValue(genText)
                //insertInVariable()
                delay(100)
            }

            //textLayoutMessage.postValue(true)

        }




    }

    fun insertInVariable(){

        textViewAcao.value = textoParaParticionar

    }

    fun settLifePoint(){

        petMutable.value!!.ID_PET = pet.value!!.ID_PET
        petMutable.value!!.ID_USER = pet.value!!.ID_USER
        petMutable.value!!.PET_NAME = pet.value!!.PET_NAME
        petMutable.value!!.TYPE_PET = pet.value!!.TYPE_PET
        petMutable.value!!.VIDA.value = pet.value!!.VIDA
        petMutable.value!!.ATAQUE.value = pet.value!!.ATAQUE
        petMutable.value!!.DEFESA.value = pet.value!!.DEFESA
        petMutable.value!!.SEDE.value = pet.value!!.SEDE
        petMutable.value!!.FOME.value = pet.value!!.FOME
        petMutable.value!!.PET_HOUR = pet.value!!.PET_HOUR
        petMutable.value!!.QTD_COCO = pet.value!!.QTD_COCO
        petMutable.value!!.BIT_MONSTER_COIN = pet.value!!.BIT_MONSTER_COIN


        MaxLifePoint.value = "/" + pet.value!!.VIDA.toString()
        PetLifeBarMaxObserver.value = petMutable.value!!.VIDA.value!!

    }

    fun settVillainLifePoint(){

        MaxLifePointsVillain.value = "/" + inimigo.value!!.vida.value
        petLifeBarVillainMax.value = inimigo.value!!.vida.value

        //A vida do proximo monstro é resetada para o MAXIMO de novo
        VillainLifePointsObserver.value = inimigo.value!!.vida.value

    }


    fun reduceLifePointVillain(dano: Int){

        val  scope = CoroutineScope(Dispatchers.Default)
        var scopStop = 0
            scope.launch {
                var controlerDamage = 0
                var vida = inimigo.value!!.vida.value!!
                while(controlerDamage < dano){
                    if(vida <= 0){

                    }
                    else{
                        vida -= 1
                    }

                    inimigo.value!!.vida.postValue(vida)
                    //VillainLifePointsObserver.postValue(vida)
                    controlerDamage++
                    delay(10)
                }

            }


    }

    fun reduceLifePointsPet(dano: Int){

        val  scope = CoroutineScope(Dispatchers.Default)
        var scopStop = 0
        scope.launch {
            var controlerDamage = 0
            var vida = petMutable.value!!.VIDA.value!!
            while(controlerDamage < dano){
                vida -= 1

                petMutable.value!!.VIDA.postValue(vida)
                //PetLifePointsObserver.postValue(vida)
                controlerDamage++
                delay(10)
            }

        }

    }

    fun change_monster(){

        var monstros: MutableList<Monstros> = mutableListOf(
            Monstros("Succubus", MutableLiveData(1000),MutableLiveData(100), MutableLiveData(R.drawable.monster1)),
            Monstros("Succubus", MutableLiveData(1000),MutableLiveData(100), MutableLiveData(R.drawable.monster2)),
            Monstros("Succubus", MutableLiveData(5000),MutableLiveData(100), MutableLiveData(R.drawable.monster3)),
            Monstros("Succubus", MutableLiveData(3000),MutableLiveData(100), MutableLiveData(R.drawable.monster4)),
            Monstros("Succubus", MutableLiveData(8000),MutableLiveData(100), MutableLiveData(R.drawable.monster5)),
            Monstros("Succubus", MutableLiveData(10000),MutableLiveData(100), MutableLiveData(R.drawable.monster6))
        )

        if(inimigo.value!!.vida.value!! <= 0){
            monstros.shuffle()
            inimigo.value!!.nome = monstros[0].nome
            inimigo.value!!.vida.value = monstros[0].vida.value!!
            inimigo.value!!.ataque.value = monstros[0].ataque.value
            inimigo.value!!.image.value = monstros[0].image.value


            //binding.villain.setImageResource(inimigo.value!!.image)
            //petLifeBarVillainMax.value = inimigo.value!!.vida.value!!
            //inimigo.value!!.vida.value = monstros[0].vida.value!!
            settVillainLifePoint()

        }

    }


//    fun getinfoPet() : PetModel{
//        var petModel : PetModel = repository.getPetInfo(SecurityPreferences(getApplication()).getUserID())
//        return petModel
//    }


}