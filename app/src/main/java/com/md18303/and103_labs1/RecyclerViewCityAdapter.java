package com.md18303.and103_labs1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewCityAdapter extends RecyclerView.Adapter<RecyclerViewCityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CityModel> listCityModel;
    private HomeActivity homeActivity;

    public RecyclerViewCityAdapter(Context context, ArrayList<CityModel> listCityModel, HomeActivity homeActivity) {
        this.context = context;
        this.listCityModel = listCityModel;
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public RecyclerViewCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCityAdapter.ViewHolder holder, int position) {
        CityModel cityModel = listCityModel.get(position);
        holder.tv_stt.setText(String.valueOf(holder.getAdapterPosition() + 1));
        holder.tv_name.setText(cityModel.getName());
        holder.tv_population.setText(String.valueOf(cityModel.getPopulation()));
        holder.tv_country.setText(cityModel.getCountry());
    }

    @Override
    public int getItemCount() {
        return listCityModel != null ? listCityModel.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stt,tv_name,tv_population,tv_country;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stt = itemView.findViewById(R.id.tv_stt);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_population = itemView.findViewById(R.id.tv_population);
            tv_country = itemView.findViewById(R.id.tv_country);
        }
    }
}
