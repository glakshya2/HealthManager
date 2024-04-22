package com.glakshya2.healthmanager.nutrition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Meal;

import java.util.ArrayList;

public class NutritionAdapter extends RecyclerView.Adapter<MealViewHolder> {

    @NonNull
    Context context;

    ArrayList<Meal> mealList;

    public NutritionAdapter(@NonNull Context context) {
        this.context = context;
        this.mealList = NutritionHelper.mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_datacard, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.dataTitleTv.setText(mealList.get(position).getName());
        holder.dataCarbsTv.setText(String.valueOf(mealList.get(position).getCarbohydrates()));
        holder.dataProtienTv.setText(String.valueOf(mealList.get(position).getProtein()));
        holder.dataFatTv.setText(String.valueOf(mealList.get(position).getFat()));
        holder.dataCaloriesTv.setText(String.valueOf(mealList.get(position).getCalories()));
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }
}
