package com.example.bitpets.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.ImageView
import com.example.bitpets.Monstros.PetItens
import com.example.bitpets.repository.HomeFragmentRepository
import com.example.bitpets.room.entities.PET
import com.example.bitpets.room.entities.PET_ITENS
import com.example.bitpets.ui.dashboard.CardItem
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val repository = HomeFragmentRepository.getInstance(application)

    var petModel = MutableLiveData<PET>()

    var petList = mutableListOf<PetItens>()

    var petItens = MutableLiveData<List<PetItens>>()

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }

    private val gif = MutableLiveData<ImageView>().apply {
        value = null
    }

    val gifMutable: LiveData<ImageView> = gif

    val text: LiveData<String> = _text

    //-----------------------PRECISA FAZER ROOMM----------------------

    fun getinfoPet(userID: String): Unit {
        petModel.value = repository.getPetInfo(userID)
    }
    fun updatePoopQuantity(userID: String): Int {
        return repository.updatePetPoopQuantity(userID, petModel.value!!.QTD_COCO)
    }

    fun updateReducePoopQuantity(userID: String): Int {
        return repository.updatePetPoopQuantity(userID, petModel.value!!.QTD_COCO-1)
    }

    fun updateHour(userID: String): Int{
        return repository.updatePetInfoHour(userID, Clock.System.now().toString())
    }

    fun updateMoney(userID: String): Int{
        return repository.updatePetMoney(userID, petModel.value!!.BIT_MONSTER_COIN+1)
    }

    fun updateHungry(userID: String): Int{
        return repository.updatePetHungry(userID, petModel.value!!.FOME)
    }

    fun getPetItensInfo(userID: String){
        getinfoPet(userID)
        var petItensEntity = repository.getPetItensInfo(petModel.value!!.ID_PET.toString())

        addArrayItensPet(petItensEntity)
        print(petList)

    }

    fun checkPoopHour() {

        val diferencaAll = Clock.System.now() - Instant.parse(petModel.value!!.PET_HOUR)

        val diferenca = diferencaAll.inWholeMinutes.toInt()

        var somaPoop = petModel.value!!.QTD_COCO + (diferenca / 5)

        if (somaPoop >= 6) {
            petModel.value!!.QTD_COCO = 6
        } else if (somaPoop < 0) {
            petModel.value!!.QTD_COCO = 0
        } else {
            petModel.value!!.QTD_COCO = somaPoop
        }

    }


    fun calculateHungry(){

        var diferencaAll = Clock.System.now() - Instant.parse(petModel.value!!.PET_HOUR)

        val diferenca = diferencaAll.inWholeMinutes.toInt() / 5
        var somaHungry = petModel.value!!.FOME - diferenca

        if(somaHungry <= 0){
            petModel.value!!.FOME = 0
        }
        else{
            petModel.value!!.FOME = somaHungry
        }

    }

    fun addArrayItensPet(petEntity: List<PET_ITENS>){

        petList.clear()

        for (petItem in petEntity){

            petList.add(PetItens(petItem.ID_ITEM, petItem.ID_PET, MutableLiveData(petItem.TYPE),MutableLiveData(petItem.FOME),MutableLiveData(petItem.DANO),MutableLiveData(petItem.DEFESA),MutableLiveData(petItem.ITEM_IMAGE)))

        }

        petItens = MutableLiveData<List<PetItens>>(petList)

    }

    fun useItem(userID: String, index: Int){
        if(petList.size > index){
            var petItenEntity = convertToPetItemEntity(petList[index])


            repository.updatePetHungry(userID,checkpetHungry(index))
            //delete do banco
            repository.deletePetItem(petItenEntity)
            //pega do banco
            getPetItensInfo(userID)
        }


    }

    fun convertToPetItemEntity( petItens: PetItens): PET_ITENS{

        var petItemEntity: PET_ITENS = PET_ITENS()

        petItemEntity.ID_ITEM = petItens.ID_ITEM
        petItemEntity.ID_PET = petItens.ID_PET
        petItemEntity.TYPE = petItens.TYPE.value!!
        petItemEntity.FOME = petItens.FOME.value!!
        petItemEntity.DANO = petItens.DANO.value!!
        petItemEntity.DEFESA = petItens.DEFESA.value!!
        petItemEntity.ITEM_IMAGE = petItens.ITEM_IMAGE.value!!

        return petItemEntity

    }

    fun checkpetHungry(index: Int): Int{

        if((petModel.value!!.FOME + petList[index].FOME.value!!.toInt()) > 100){
            return 100
        }

        else{
            return petModel.value!!.FOME + petList[index].FOME.value!!.toInt()
        }

    }



//    fun updateInfoPet(userID: String, data: String): PET {
//
//        var petModel: PET = repository.updatePetInfoHour(userID, data)
//
//        petModel
//
//        return petModel
//
//    }



}