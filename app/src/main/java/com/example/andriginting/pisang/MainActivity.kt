package com.example.andriginting.pisang

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        btn_login.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        
    }

}
