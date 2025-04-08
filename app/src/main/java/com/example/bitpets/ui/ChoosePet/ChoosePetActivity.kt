package com.example.bitpets.ui.ChoosePet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bitpets.R
import com.example.bitpets.databinding.ActivityChoosePetBinding
import com.example.bitpets.ui.ChoosePetSelector.ChoosePetSelectorActivity

class ChoosePetActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityChoosePetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChoosePetBinding.inflate(layoutInflater)

        binding.Escolher.setOnClickListener(this)
        setContentView(binding.root)


        supportActionBar?.hide()

    }




    override fun onClick(v: View) {
        if(v.id == R.id.Escolher){

            startActivity(Intent(this, ChoosePetSelectorActivity::class.java))
            finish()

            //binding.Escolher.visibility = View.GONE
            //binding.Linear.visibility = View.GONE
            //binding.kafraImage.visibility = View.GONE
            //binding.backgroundId.setBackgroundColor(Color.parseColor("#EFA1BC"))
        }
    }


}