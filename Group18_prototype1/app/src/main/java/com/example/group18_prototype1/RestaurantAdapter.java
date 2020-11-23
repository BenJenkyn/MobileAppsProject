package com.example.group18_prototype1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private Context context;
    private List<Restaurants> restaurantList;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView restaurantName;
        public TextView restaurantAddress;
        public TextView phoneNum;
        public TextView restaurantTags;
        public TextView restaurantDescription;
        public TextView id;

        public MyViewHolder(@NonNull View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurantName);
            restaurantAddress = view.findViewById(R.id.restaurantAddress);
            phoneNum = view.findViewById(R.id.phoneNum);
            restaurantTags = view.findViewById(R.id.restaurantTags);
            restaurantDescription = view.findViewById(R.id.restaurantDescription);
            id = view.findViewById(R.id.restaurant_id);
        }
    }

    public RestaurantAdapter(Context context, List<Restaurants> restaurants){
        this.context = context;
        this.restaurantList = restaurants;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View restaurantView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(restaurantView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Restaurants restaurant = restaurantList.get(position);
        holder.id.setText(""+restaurant.getId());
        holder.restaurantName.setText(restaurant.getName());
        holder.restaurantAddress.setText(restaurant.getAddress());
        holder.phoneNum.setText(""+restaurant.getPhoneNum());
        holder.restaurantTags.setText(restaurant.getTags());
        holder.restaurantDescription.setText(restaurant.getDescription());

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


}
