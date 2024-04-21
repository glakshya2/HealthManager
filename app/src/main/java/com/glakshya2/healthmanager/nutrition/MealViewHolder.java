package com.glakshya2.healthmanager.nutrition;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.R;

public class MealViewHolder extends RecyclerView.ViewHolder {
    TextView dataTitleTv, dataCarbsTv, dataProtienTv, dataFatTv, dataCaloriesTv;

    public MealViewHolder(@NonNull View itemView) {
        super(itemView);
        dataTitleTv = itemView.findViewById(R.id.datacard_titleTv);
        dataCarbsTv = itemView.findViewById(R.id.datacard_carbsTv);
        dataProtienTv = itemView.findViewById(R.id.datacard_protienTv);
        dataFatTv = itemView.findViewById(R.id.datacard_fatTv);
        dataCaloriesTv = itemView.findViewById(R.id.datacard_caloriesTv);
    }
}
