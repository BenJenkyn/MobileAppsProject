package com.example.personalrestauantguide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.personalrestauantguide.models.Restaurant
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.restaurant_card.*


class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        loadData()
    }

    private fun loadData(){

        val pageData = intent.getSerializableExtra("RESTAURANT")
        val restaurant = pageData as Restaurant

        txt_res_name.text = "NAME: ${restaurant.name}"
        txt_res_address.text = "ADDRESS: ${restaurant.address}"
        txt_res_phone.text = "PHONE: ${restaurant.phone}"
        txt_res_tags.text = "TAGS: ${restaurant.tags}"
        rb_detail.rating = restaurant.rating.toFloat()
        Picasso.get().load(restaurant.image).into(img_restaurant_detail)

        fab_map.setOnClickListener {
           val intent =  Intent(this, MapsActivity::class.java).putExtra("lat", restaurant.lat).putExtra(
               "lon",
               restaurant.lon
           ).putExtra("name", restaurant.name)
            startActivity(intent)
        }

        fab_twitter.setOnClickListener {
            val tweetData = "Check out this restaurant: ${restaurant.name} at: ${restaurant.address}"
            val tweetUrl = "https://twitter.com/intent/tweet?text=$tweetData"
            val uri: Uri = Uri.parse(tweetUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        fab_fb.setOnClickListener {
            val tweetData = "Check out this restaurant: ${restaurant.name} at: ${restaurant.address}"
            val sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=$tweetData"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
            startActivity(intent)
        }

        fab_email.setOnClickListener {
            val tweetData = "Check out this restaurant: ${restaurant.name} at: ${restaurant.address}"
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "image/jpeg"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(""))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PRG Share")
            emailIntent.putExtra(Intent.EXTRA_TEXT, tweetData)
            startActivity(emailIntent)
        }

        fab_rate.setOnClickListener {
            rateData(restaurant.id)
        }

    }

    private fun rateData(id:Int){

        val view = LayoutInflater.from(this).inflate(R.layout.rating_layout,null, false)
        val btn = view.findViewById<Button>(R.id.btn_rate)
        val edt = view.findViewById<TextInputEditText>(R.id.edt_rating)

        val dialog = AlertDialog.Builder(this).setTitle("Rate this restaurant").setView(view)
        val actual = dialog.create()
        actual.show()

        btn.setOnClickListener {
            if(!edt.text?.isEmpty()!!){
                val rating = edt.text.toString()
                if(rating.toDouble() > 5 || rating.toDouble() < 0) Snackbar.make(view,"Kindly use a valid rating", Snackbar.LENGTH_LONG).show()
                else {
                    val update =
                        "UPDATE restaurant SET rating = '${rating}' WHERE id = '${id}'"
                    val db = openOrCreateDatabase(Authentication().dbName, MODE_PRIVATE, null)
                    db.execSQL(update)
                    actual.dismiss()
                }
            }else{
                Snackbar.make(view,"You need to add a rating first", Snackbar.LENGTH_LONG).show()
            }
        }



    }



}