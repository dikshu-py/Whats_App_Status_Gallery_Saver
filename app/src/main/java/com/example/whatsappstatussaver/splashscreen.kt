package com.example.whatsappstatussaver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashscreen : AppCompatActivity() {
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        handler = Handler()
        handler.postDelayed({
            var intent : Intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        },3000)


    }
}
