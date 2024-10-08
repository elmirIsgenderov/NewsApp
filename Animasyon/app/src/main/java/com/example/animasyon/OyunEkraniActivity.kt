package com.example.animasyon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animasyon.databinding.ActivityOyunEkraniBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {

    //Pozisyonlar.
    private var anaKarakterX = 0.0f
    private var anaKarakterY = 0.0f
    private var siyahKarakterX = 0.0f
    private var siyahKarakterY = 0.0f
    private var sariKarakterX = 0.0f
    private var sariKarakterY = 0.0f
    private var kirmiziKarakterX = 0.0f
    private var kizmiziKarakterY = 0.0f

    //Boyutlar.
    private var ekranGenisliyi = 0
    private var ekranYuksekliyi = 0
    private var anakarakterGenisliyi = 0
    private var anakarakterYuksekliyi = 0

    //Kontroller.
    private var dokunmaKontrol = false
    private var baslangicKontrol = false
    private val timer = Timer()
    private var skor=0

    private lateinit var binding: ActivityOyunEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOyunEkraniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tasarimCL)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.imgQara.x=-800f
        binding.imgQara.y=-800f
        binding.imgSari.x=-800f
        binding.imgSari.y=-800f
        binding.imgKirmizi.x=-800f
        binding.imgKirmizi.y=-800f

        binding.tasarimCL.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if (baslangicKontrol) {
                    if (event?.action == MotionEvent.ACTION_DOWN) {
                        Log.e("MotionEvent", "Ekrana Dokunuldu!!!")
                        dokunmaKontrol = true
                    }
                    if (event?.action == MotionEvent.ACTION_UP) {
                        Log.e("MotionEvent", "Ekran Birakildi!!!")
                        dokunmaKontrol = false
                    }
                } else {

                    baslangicKontrol = true
                    binding.txtOyunaBasla.visibility = View.INVISIBLE

                    anaKarakterX = binding.anaKarakter.x
                    anaKarakterY = binding.anaKarakter.y

                    anakarakterGenisliyi = binding.anaKarakter.width
                    anakarakterYuksekliyi = binding.anaKarakter.height
                    ekranGenisliyi = binding.tasarimCL.width
                    ekranYuksekliyi = binding.tasarimCL.height

                    timer.schedule(0, 20) {
                        Handler(Looper.getMainLooper()).post {
                           anakarakterinHereket()
                            cisimlerinHereketi()
                            carpmaKontrol()
                        }
                    }
                }
                return true
            }
        })
    }
    fun anakarakterinHereket(){

        val anakarekterinSureti=ekranYuksekliyi/90f

        if (dokunmaKontrol) { //Yuxari-Asagi Hareketidir.
            anaKarakterY -= anakarekterinSureti  // Ana Karaktern Sureti.
        } else {
            anaKarakterY += anakarekterinSureti  // Ana Karaktern Sureti.
        }
        if (anaKarakterY <= 0.0f) { //Ekran Siniridir.
            anaKarakterY = 0.0f
        }
        if (anaKarakterY >= ekranYuksekliyi - anakarakterYuksekliyi) { //Ekran Siniridir.
            anaKarakterY = (ekranYuksekliyi - anakarakterYuksekliyi).toFloat()
        }
        binding.anaKarakter.y = anaKarakterY
    }
    fun cisimlerinHereketi(){
        //Karakterlerin Sol Terefe Suretidir.
        siyahKarakterX-=ekranGenisliyi/44f
        sariKarakterX-=ekranGenisliyi/60f
        kirmiziKarakterX-=ekranGenisliyi/40f

        if (siyahKarakterX<0.0f){
            siyahKarakterX=ekranGenisliyi+25.0f
            siyahKarakterY= floor(Math.random()*ekranYuksekliyi).toFloat()
        }
        binding.imgQara.x= siyahKarakterX//Cismin Anlik konumunu belirler.
        binding.imgQara.y= siyahKarakterY

        if (sariKarakterX<0.0f){
            sariKarakterX=ekranGenisliyi+25.0f
            sariKarakterY= floor(Math.random()*ekranYuksekliyi).toFloat()
        }
        binding.imgSari.x= sariKarakterX
        binding.imgSari.y= sariKarakterY

        if (kirmiziKarakterX<0.0f){
            kirmiziKarakterX=ekranGenisliyi+25.0f
            kizmiziKarakterY= floor(Math.random()*ekranYuksekliyi).toFloat()
        }
        binding.imgKirmizi.x= kirmiziKarakterX
        binding.imgKirmizi.y= kizmiziKarakterY
    }
    fun carpmaKontrol(){

        val sariMerkezX= sariKarakterX+anakarakterGenisliyi/2
        val sariMerkezY= sariKarakterY+anakarakterYuksekliyi/2

        if (0.0f<=sariMerkezX && sariMerkezX<=anakarakterGenisliyi
            && anaKarakterY<= sariMerkezY && sariMerkezY<=anaKarakterY+anakarakterYuksekliyi){

            skor+=20
            sariKarakterX=-10.0f
        }

        val kirmiziMerkezX= kirmiziKarakterX+anakarakterGenisliyi/2
        val kirmiziMerkezY= kizmiziKarakterY+anakarakterYuksekliyi/2

        if (0.0f<=kirmiziMerkezX && kirmiziMerkezX<=anakarakterGenisliyi
            && anaKarakterY<= kirmiziMerkezY && kirmiziMerkezY<=anaKarakterY+anakarakterYuksekliyi){

            skor+=50
            kirmiziKarakterX=-10.0f
        }

        val siyahMerkezX= siyahKarakterX+anakarakterGenisliyi/2
        val siyahMerkezY= siyahKarakterY+anakarakterYuksekliyi/2

        if (0.0f<=siyahMerkezX && siyahMerkezX<=anakarakterGenisliyi
            && anaKarakterY<= siyahMerkezY && siyahMerkezY<=anaKarakterY+anakarakterYuksekliyi){

            siyahKarakterX=-10.0f
            timer.cancel()

            val intent= Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
        binding.txtSkor.text=skor.toString()
    }
}