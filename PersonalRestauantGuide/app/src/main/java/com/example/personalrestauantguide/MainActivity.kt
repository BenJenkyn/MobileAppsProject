package com.example.personalrestauantguide

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalrestauantguide.adapters.RestaurantAdapter
import com.example.personalrestauantguide.models.Restaurant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var db:SQLiteDatabase
    private val tableName = "restaurant"
    private lateinit var adapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDB()
        loadData()
    }

    private fun initializeDB(){
        //initialize database
        db = openOrCreateDatabase(Authentication().dbName, Context.MODE_PRIVATE, null)
        //initialize tables
        initializeTable()
    }

    //method to create table
    private fun initializeTable(){
        val createTable = "CREATE TABLE IF NOT EXISTS $tableName(id INTEGER PRIMARY KEY AUTOINCREMENT, name text, address text, phone text, lat text, lon text, tags text, image text, rating text)"
        //execute the query
        db.execSQL(createTable)
        initializeData()
    }

    //add data to db if new application
    private fun initializeData(){
        val sharedPref = getSharedPreferences("PRG", MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirst",false)

        if(!isFirstTime){
            val res1 = Restaurant("The Barbary","West End, London","+44222333",51.5144,-0.1262,"North African Cuisines","https://thebarbary.co.uk/wp-content/themes/barbary/dist/img/barbaryImgSq02.jpg",0,4.6)
            val res2 = Restaurant("Crudo Cevicheria","Unit D, Retail, 211C Old St, London EC1V 9NR, United Kingdom","+441222333",51.3144,-0.1262,"Dine-in, Takeaway, No-contact delivery","https://res.cloudinary.com/fittco/image/upload/v1574709905/nwvnm0tokobq2dw8zfp8.jpg",0,5.0)
            val res3 = Restaurant("Ormer Mayfair Restaurant","Flemings Mayfair, 7-12 Half Moon St, London W1J 7BH, United Kingdom","+442722333",51.3244,-0.1362,"British ingredients, Wood-panelled","https://www.telegraph.co.uk/content/dam/Travel/hotels/europe/united-kingdom/Hotels%20-%20England/london/ormer-mayfair-flemings-mayfair.jpg",0,4.7)
            val res4 = Restaurant("Sushi Tetsu","12 Jerusalem Passage, Farringdon, London EC1V 4JP, United Kingdom","+4422352333",51.3244,-0.2372,"sushi and sashimi","https://media-cdn.tripadvisor.com/media/photo-s/02/eb/aa/96/sushi-tetsu.jpg",0,4.8)
            val res5 = Restaurant("Angler","South Place Hotel, 3 South Pl, London EC2M 2AF, United Kingdom","+462212333",51.4244,-0.2372,"Mirrored Cielling","https://cdn.vox-cdn.com/thumbor/xai_c5X3MjrUGjP2RVy8EBPJ8Qg=/0x0:2000x1335/1200x675/filters:focal(840x508:1160x828)/cdn.vox-cdn.com/uploads/chorus_image/image/64017888/2019_06_12_Angler_002.0.jpg",0,4.6)

            val rs:ArrayList<Restaurant> = ArrayList<Restaurant>()
            rs.add(res1)
            rs.add(res2)
            rs.add(res3)
            rs.add(res4)
            rs.add(res5)

            for (i in 0 until rs.size){
                val insert = "INSERT into $tableName(name, address, phone, lat, lon, tags, image, rating) VALUES ('${rs[i].name}','${rs[i].address}','${rs[i].phone}','${rs[i].lat}','${rs[i].lon}'" +
                        ",'${rs[i].tags}','${rs[i].image}','${rs[i].rating}')"
                db.execSQL(insert)
            }

            sharedPref.edit().putBoolean("isFirst",true).apply()

        }
    }

    //load data
    private fun loadData(){
        rv_restaurants.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )
        rv_restaurants.itemAnimator = DefaultItemAnimator()
        val data:ArrayList<Restaurant>  = ArrayList()
        val queryData = "SELECT * FROM $tableName"
        val cursor:Cursor = db.rawQuery(queryData,null)
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val address = cursor.getString(2)
                val phone = cursor.getString(3)
                val lat = cursor.getString(4)
                val lon = cursor.getString(5)
                val tags = cursor.getString(6)
                val image = cursor.getString(7)
                val rating = cursor.getString(8)

                val rest = Restaurant(name,address,phone,lat.toDouble(),lon.toDouble(),tags,image,id,rating.toDouble())
                data.add(rest)
                Log.d("Data:",lat)

            }while (cursor.moveToNext())
        }
        cursor.close()
        adapter = RestaurantAdapter(this@MainActivity,data)
        rv_restaurants.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_main,menu)

        val search = menu?.findItem(R.id.action_search)
        val view = search?.actionView as SearchView

        view.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                adapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_search){

        }else{
            startActivity(Intent(this@MainActivity,About::class.java))
        }

        return super.onOptionsItemSelected(item)
    }


}