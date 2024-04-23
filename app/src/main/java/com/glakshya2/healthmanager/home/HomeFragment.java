package com.glakshya2.healthmanager.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.glakshya2.healthmanager.R;
import com.glakshya2.healthmanager.nutrition.NutritionFragment;
import com.glakshya2.healthmanager.profile.ProfileFragment;
import com.glakshya2.healthmanager.records.HealthRecordsFragment;
import com.glakshya2.healthmanager.reminders.RemindersFragment;
import com.glakshya2.healthmanager.schema.FitnessData;
import com.glakshya2.healthmanager.schema.Meal;
import com.glakshya2.healthmanager.schema.User;
import com.glakshya2.healthmanager.tracking.HealthTrackingFragment;
import com.glakshya2.healthmanager.utils.ChildToHost;


public class HomeFragment extends Fragment {

    CardView healthTrackingCard, nutritionCard, recordsCard, remindersCard, profileCard, logoutCard;
    TextView stepsTv, distanceTv, caloriesTv, minutesTv;
    TextView carbsTv, proteinTv, fatTv, caloriesNutritionTv;
    LinearLayout healthTrackingLayout, nutritionLayout, recordsLayout, remindersLayout, profileLayout, logout_layout;
    ChildToHost childToHost;

    User user;
    FitnessData fitnessData;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        childToHost = (ChildToHost) context;
    }

    public void receiveData(User user, FitnessData fitnessData) {
        if (user != null) {
            this.user = user;
        }

        if (fitnessData != null) {
            this.fitnessData = fitnessData;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        healthTrackingCard = view.findViewById(R.id.healthTrackingCard);
        nutritionCard = view.findViewById(R.id.nutritionCard);
        recordsCard = view.findViewById(R.id.recordsCard);
        remindersCard = view.findViewById(R.id.remindersCard);
        profileCard = view.findViewById(R.id.profileCard);
        logoutCard = view.findViewById(R.id.logoutCard);

        stepsTv = view.findViewById(R.id.steps_healthTracking_tv);
        distanceTv = view.findViewById(R.id.distance_health_tracking_tv);
        caloriesTv = view.findViewById(R.id.calories_health_tracking_tv);
        minutesTv = view.findViewById(R.id.minutes_health_tracking_tv);

        carbsTv = view.findViewById(R.id.carbohydrates_nutrition_tv);
        proteinTv = view.findViewById(R.id.protein_nutrition_tv);
        fatTv = view.findViewById(R.id.fat_nutrition_tv);
        caloriesNutritionTv = view.findViewById(R.id.calories_nutrition_tv);

        healthTrackingLayout = view.findViewById(R.id.healthTrackingLayout);
        nutritionLayout = view.findViewById(R.id.nutrition_layout);
        recordsLayout = view.findViewById(R.id.records_layout);
        remindersLayout = view.findViewById(R.id.reminders_layout);
        profileLayout = view.findViewById(R.id.profile_layout);
        logout_layout = view.findViewById(R.id.logout_layout);

        healthTrackingLayout.setOnClickListener(v -> childToHost.setFragment(new HealthTrackingFragment()));
        nutritionLayout.setOnClickListener(v -> childToHost.setFragment(new NutritionFragment()));
        recordsLayout.setOnClickListener(v -> childToHost.setFragment(new HealthRecordsFragment()));
        remindersLayout.setOnClickListener(v -> childToHost.setFragment(new RemindersFragment()));
        profileLayout.setOnClickListener(v -> childToHost.setFragment(new ProfileFragment()));
        logout_layout.setOnClickListener(v -> childToHost.logout());
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        if (this.user == null) {
            this.user = new User();
        }
        if (this.fitnessData == null) {
            this.fitnessData = new FitnessData();
        }

        stepsTv.setText(String.valueOf(fitnessData.getSteps()));
        distanceTv.setText(String.valueOf(fitnessData.getDistance()));
        caloriesTv.setText(String.valueOf(fitnessData.getCalories()));
        minutesTv.setText(String.valueOf(fitnessData.getMoveMinutes()));

        float totalCarbs = 0.0F, totalProteins = 0.0F, totalCalories = 0.0F, totalFats = 0.0F;
        for (Meal meal : this.user.getNutrition().getMealList()) {
            totalCarbs += meal.getCarbohydrates();
            totalFats += meal.getFat();
            totalCalories += meal.getCalories();
            totalProteins += meal.getProtein();
        }

        carbsTv.setText(String.valueOf(totalCarbs));
        proteinTv.setText(String.valueOf(totalProteins));
        caloriesNutritionTv.setText(String.valueOf(totalCalories));
        fatTv.setText(String.valueOf(totalFats));
    }
}