package com.glakshya2.healthmanager.nutrition;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.Meal;
import com.glakshya2.healthmanager.schema.Nutrition;
import com.glakshya2.healthmanager.utils.ChildToHost;

public class AddNutritionFragment extends Fragment {

    EditText nameEt, carbsEt, proteinEt, fatEt, caloriesEt;
    Button saveBtn;
    ChildToHost childToHost;
    public AddNutritionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_nutrition, container, false);
        nameEt = view.findViewById(R.id.addNutrition_name);
        carbsEt = view.findViewById(R.id.addNutrition_carbs);
        proteinEt = view.findViewById(R.id.addNutrition_protien);
        fatEt = view.findViewById(R.id.addNutrition_fat);
        caloriesEt = view.findViewById(R.id.addNutrition_calories);
        saveBtn = view.findViewById(R.id.addNutrition_save);
        saveBtn.setOnClickListener(v -> {
            NutritionHelper.mealList.add(new Meal(nameEt.getText().toString(),
                    Float.parseFloat(carbsEt.getText().toString()),
                    Float.parseFloat(proteinEt.getText().toString()),
                    Float.parseFloat(fatEt.getText().toString()),
                    Float.parseFloat(caloriesEt.getText().toString())));
            childToHost.transferData(new Nutrition(NutritionHelper.mealList));
        });

        return view;
    }
}