package com.glakshya2.healthmanager.nutrition;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glakshya2.healthmanager.ChildToHost;
import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Meal;
import com.glakshya2.healthmanager.schema.Nutrition;

import java.util.ArrayList;

public class NutritionFragment extends Fragment {

    Button addMealBtn;
    TextView carbsTv, protienTv, fatTv, calTv;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ChildToHost childToHost;
    Nutrition nutrition;

    public NutritionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition, container, false);
        carbsTv = view.findViewById(R.id.carbsTv);
        protienTv = view.findViewById(R.id.protienTv);
        fatTv = view.findViewById(R.id.fatTv);
        calTv = view.findViewById(R.id.calTv);
        addMealBtn = view.findViewById(R.id.addMealBtn);
        addMealBtn.setOnClickListener(v -> childToHost.setFragment(new AddNutritionFragment()));

        recyclerView = view.findViewById(R.id.nutrition_recycler);
        adapter = new NutritionAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        if (this.nutrition != null) {
            NutritionHelper.mealList = this.nutrition.getMealList();
        } else {
            NutritionHelper.mealList = new ArrayList<Meal>();
        }
        float totalCarbs = 0.0f, totalProtiens = 0.0f, totalFats = 0.0f, totalCalories = 0.0f;
        for (Meal meal : NutritionHelper.mealList) {
            totalCarbs += meal.getCarbohydrates();
            totalProtiens += meal.getProtein();
            totalFats += meal.getFat();
            totalCalories += meal.getCalories();
        }
        carbsTv.setText(String.valueOf(totalCarbs));
        protienTv.setText(String.valueOf(totalProtiens));
        fatTv.setText(String.valueOf(totalFats));
        calTv.setText(String.valueOf(totalCalories));
    }

    public void recieveData(Nutrition nutrition) {
        if (nutrition != null) {
            this.nutrition = nutrition;
        } else {
            this.nutrition = new Nutrition(new ArrayList<Meal>());
        }
    }


}