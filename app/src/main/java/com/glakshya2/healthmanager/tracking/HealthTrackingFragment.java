package com.glakshya2.healthmanager.tracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.schema.FitnessData;


public class HealthTrackingFragment extends Fragment {

    TextView steps, calories, distance, moveMin;
    public HealthTrackingFragment() {
        // Required empty public constructor
    }
    FitnessData fitnessData;

    public void receiveData(FitnessData fitnessData) {
        if (fitnessData != null) {
            this.fitnessData = fitnessData;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_tracking, container, false);
        steps = view.findViewById(R.id.stepsValueTv);
        calories = view.findViewById(R.id.caloriesValueTv);
        distance = view.findViewById(R.id.distanceValueTv);
        moveMin = view.findViewById(R.id.moveMinsTv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        if (this.fitnessData != null) {
            steps.setText(String.valueOf(this.fitnessData.getSteps()));
            calories.setText(String.valueOf(this.fitnessData.getCalories()));
            distance.setText(String.valueOf(this.fitnessData.getDistance()));
            moveMin.setText(String.valueOf(this.fitnessData.getMoveMinutes()));
        }
    }
}