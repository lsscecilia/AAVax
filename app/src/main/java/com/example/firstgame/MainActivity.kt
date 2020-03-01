package com.example.firstgame

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        clickmebut.setOnClickListener {
            d("marcus", "button is pressed fagot")
            d("marcus", "button not pressed hahahahahahaa")
            d("marcus", "dgrdgregegregr")
            d("marcus", "hahayousuck")
            startActivity(Intent(this, TravelActivity::class.java))
        }
    }
}
