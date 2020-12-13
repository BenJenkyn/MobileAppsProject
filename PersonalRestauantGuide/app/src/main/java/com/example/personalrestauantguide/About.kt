package com.example.personalrestauantguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_about.*

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        showList()
    }

    private fun showList(){
        val members = arrayOf("Name: Matthew Mukherjee\nStudent ID: 101190394","NAME: Benjamin Jenkyn\nStudent ID: 101184985","NAME: Abdurahman Ahmed\nStudent ID: 101207567")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,members)
        team_list.adapter = adapter
    }

}