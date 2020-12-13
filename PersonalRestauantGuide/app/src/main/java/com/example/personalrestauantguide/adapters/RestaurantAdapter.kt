package com.example.personalrestauantguide.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalrestauantguide.Detail
import com.example.personalrestauantguide.R
import com.example.personalrestauantguide.models.Restaurant
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_card.view.*

class RestaurantAdapter(private var mCtx:Activity, private var restaurants:ArrayList<Restaurant> ) : RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder>()
, Filterable{

    var nameTagFilterList = ArrayList<Restaurant>()
    init {
        nameTagFilterList = restaurants
    }

    class RestaurantHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val iamge:AppCompatImageView = itemView.findViewById(R.id.img_restaurant)
        val name:AppCompatTextView = itemView.findViewById(R.id.txt_restaurant_name)
        val rating: RatingBar = itemView.findViewById(R.id.restaurant_rating)
        val card:MaterialCardView = itemView.findViewById(R.id.card_restaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        return RestaurantHolder(LayoutInflater.from(mCtx).inflate(R.layout.restaurant_card,parent,false))
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {

        val restaurant = nameTagFilterList.get(position)
        holder.name.text = restaurant.name.trim()
        Picasso.get().load(restaurant.image).into(holder.iamge)
        holder.rating.rating = restaurant.rating.toFloat()
        holder.card.setOnClickListener {
            val x = Intent(mCtx,Detail::class.java).putExtra("RESTAURANT",restaurant)
            mCtx.startActivity(x)
        }

    }

    override fun getItemCount(): Int {
        return nameTagFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val search = constraint.toString()
                if(search.isEmpty()){
                    nameTagFilterList = restaurants as ArrayList<Restaurant>
                }else{
                    val results = ArrayList<Restaurant>()
                    for (row in restaurants){
                        if(row.tags.toLowerCase().contains(search.toLowerCase())  ||  row.name.toLowerCase().contains(search.toLowerCase())){
                            results.add(row)
                        }
                    }
                    nameTagFilterList = results
                }

                val filterResults = FilterResults()
                filterResults.values = nameTagFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                nameTagFilterList = results?.values as ArrayList<Restaurant>
                notifyDataSetChanged()
            }

        }
    }

}