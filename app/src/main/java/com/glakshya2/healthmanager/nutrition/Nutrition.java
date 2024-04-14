package com.glakshya2.healthmanager.nutrition;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.ChildToHost;
import com.glakshya2.healthmanager.R;

public class Nutrition extends Fragment {

    Button addMeal;
    ChildToHost childToHost;

    public Nutrition() {
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
        addMeal = view.findViewById(R.id.addMealBtn);
        addMeal.setOnClickListener(v -> {
            childToHost.transferData(new AddNutrition());
        });
        return view;
    }
}