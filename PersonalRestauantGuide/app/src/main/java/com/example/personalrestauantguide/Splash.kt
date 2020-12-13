package com.example.personalrestauantguide

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@Splash, Authentication::class.java))
        finish()

    }
}