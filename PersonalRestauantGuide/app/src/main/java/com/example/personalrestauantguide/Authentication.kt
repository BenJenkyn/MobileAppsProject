package com.example.personalrestauantguide

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_authentication.*

class Authentication : AppCompatActivity() {

    private lateinit var db:SQLiteDatabase
    private val tableName = "users"
    val dbName = "prg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        //initialize database
        initializeDB()
        //initialize ui
        initUI()

    }

    private fun initializeDB(){
        //initialize database
        db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)
        //initialize tables
        initializeTable()
    }

    //method to create table
    private fun initializeTable(){
        val createTable = "CREATE TABLE IF NOT EXISTS $tableName(id INTEGER PRIMARY KEY AUTOINCREMENT, email text, password text)"
        //execute the query
        db.execSQL(createTable)
    }

    //initialize user interface
    private fun initUI(){

        btn_login.setOnClickListener {
            login(it)
        }

        btn_register.setOnClickListener {
            register(it)
        }

    }


    private fun login(it:View){
        if (edt_email.text?.isEmpty()!! || edt_password.text?.isEmpty()!!){
            Snackbar.make(it,"Ensure you have filled in the details correctly",Snackbar.LENGTH_LONG).show()
        }else{

            val email = edt_email.text.toString()
            val pass = edt_password.text.toString()

            val loginQuery = "SELECT * FROM $tableName WHERE email = '$email' AND password = '$pass'"
            val values:Cursor = db.rawQuery(loginQuery,null)
            if(values.count < 1){
                Snackbar.make(it,"Check your credentials and try again",Snackbar.LENGTH_LONG).show()
            }else{
                val navigate = Intent(this@Authentication,MainActivity::class.java)
                startActivity(navigate)
                getSharedPreferences("PRG", MODE_PRIVATE).edit().putString("user",email).apply()
            }
            values.close()
        }
    }
    private fun register(it:View){
        if (edt_email.text?.isEmpty()!! || edt_password.text?.isEmpty()!!){
            Snackbar.make(it,"Ensure you have filled in the details correctly",Snackbar.LENGTH_LONG).show()
        }else{

            val email = edt_email.text.toString()
            val pass = edt_password.text.toString()

            val loginQuery = "SELECT * FROM $tableName WHERE email = '$email'"
            val values:Cursor = db.rawQuery(loginQuery,null)
            if(values.count > 1){
                Snackbar.make(it,"That email is already in use",Snackbar.LENGTH_LONG).show()
            }else{

                val registerQuery = "INSERT into $tableName(`email`,`password`) VALUES ('$email','$pass')"
                db.execSQL(registerQuery)
                // navigation intent
                val navigate = Intent(this@Authentication,MainActivity::class.java)
                startActivity(navigate)
                getSharedPreferences("PRG", MODE_PRIVATE).edit().putString("user",email).apply()
            }
            values.close()
        }
    }

}








