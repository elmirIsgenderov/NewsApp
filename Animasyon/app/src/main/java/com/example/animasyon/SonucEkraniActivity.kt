package com.example.animasyon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animasyon.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySonucEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySonucEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gelenSkor=intent.getIntExtra("skor",0)
        binding.txtToplamSkor.text=gelenSkor.toString()

         val sp=getSharedPreferences("Sonuc", Context.MODE_PRIVATE)
         val yuksekSkor=sp.getInt("yuksekSkor",0)

         if(gelenSkor>yuksekSkor){
            val editor=sp.edit()
             editor.putInt("yuksekSkor",gelenSkor)
             editor.apply()
             binding.txtEnYuksekSkor.text=gelenSkor.toString()
        }else{
             binding.txtEnYuksekSkor.text=yuksekSkor.toString()
         }
        binding.btnTekraarOyna.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}