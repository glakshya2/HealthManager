package com.glakshya2.healthmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.glakshya2.healthmanager.auth.Authorization;
import com.glakshya2.healthmanager.home.HomeFragment;
import com.glakshya2.healthmanager.nutrition.AddNutritionFragment;
import com.glakshya2.healthmanager.nutrition.NutritionFragment;
import com.glakshya2.healthmanager.profile.ProfileFragment;
import com.glakshya2.healthmanager.records.AddHealthRecordsFragment;
import com.glakshya2.healthmanager.records.HealthRecordsFragment;
import com.glakshya2.healthmanager.records.ViewRecord;
import com.glakshya2.healthmanager.reminders.AddReminderFragment;
import com.glakshya2.healthmanager.reminders.RemindersFragment;
import com.glakshya2.healthmanager.reminders.ViewReminderFragment;
import com.glakshya2.healthmanager.schema.FitnessData;
import com.glakshya2.healthmanager.schema.History;
import com.glakshya2.healthmanager.schema.Nutrition;
import com.glakshya2.healthmanager.schema.Profile;
import com.glakshya2.healthmanager.schema.ReminderList;
import com.glakshya2.healthmanager.schema.User;
import com.glakshya2.healthmanager.tracking.HealthTrackingFragment;
import com.glakshya2.healthmanager.utils.ChildToHost;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ChildToHost {

    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    User user;
    FitnessData fitnessData = new FitnessData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.inflateMenu(R.menu.nav_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(firebaseUser.getUid() + "/");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM}, 101);
        }
        readData();
        user = new User();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if (user == null) {
                    user = new User();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                loadFragment(new HomeFragment());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        if (menuItemId == R.id.records) {
            loadFragment(new HealthRecordsFragment());
            toolbar.setTitle("Health Records");
        } else if (menuItemId == R.id.tracking) {
            loadFragment(new HealthTrackingFragment());
            toolbar.setTitle("Health Tracking");
        } else if (menuItemId == R.id.nutrition) {
            loadFragment(new NutritionFragment());
            toolbar.setTitle("Nutrition");
        } else if (menuItemId == R.id.reminders) {
            loadFragment(new RemindersFragment());
            toolbar.setTitle("Reminders");
        } else if (menuItemId == R.id.profile) {
            loadFragment(new ProfileFragment());
        } else if (menuItemId == R.id.home) {
            loadFragment(new HomeFragment());
        } else if (menuItemId == R.id.logout) {
            signOut();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut() {
        firebaseAuth.signOut();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, googleSignInOptions).revokeAccess()
                .addOnCompleteListener(this, task -> startActivity(new Intent(this, Authorization.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

    void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
        if (fragment instanceof HomeFragment) {
            toolbar.setTitle("Home");
            HomeFragment homeFragment = (HomeFragment) fragment;
            if (user != null) {
                homeFragment.receiveData(user, fitnessData);
            }
        } else if (fragment instanceof AddNutritionFragment) {
            toolbar.setTitle("Add Meal");
        } else if (fragment instanceof NutritionFragment) {
            toolbar.setTitle("Nutrition");
            NutritionFragment nutritionFragment = (NutritionFragment) fragment;
            if (user != null) {
                nutritionFragment.receiveData(user.getNutrition());
            }
        } else if (fragment instanceof ProfileFragment) {
            toolbar.setTitle("Profile");
            ProfileFragment profileFragment = (ProfileFragment) fragment;
            if (user != null) {
                profileFragment.receiveData(user.getProfile());
            }
        } else if (fragment instanceof AddHealthRecordsFragment) {
            toolbar.setTitle("Add Health Record");
        } else if (fragment instanceof HealthRecordsFragment) {
            toolbar.setTitle("Health Records");
            HealthRecordsFragment healthRecordsFragment = (HealthRecordsFragment) fragment;
            if (user != null) {
                healthRecordsFragment.receiveData(user.getHistory());
            }
        } else if (fragment instanceof AddReminderFragment) {
            toolbar.setTitle("Add Reminder");
        } else if (fragment instanceof RemindersFragment) {
            toolbar.setTitle("Reminders");
            RemindersFragment remindersFragment = (RemindersFragment) fragment;
            if (user != null) {
                remindersFragment.receiveData(user.getReminderList());
            }
        } else if (fragment instanceof HealthTrackingFragment) {
            toolbar.setTitle("Health Tracking");
            HealthTrackingFragment healthTrackingFragment = (HealthTrackingFragment) fragment;
            if (fitnessData != null) {
                healthTrackingFragment.receiveData(fitnessData);
            }
        } else if (fragment instanceof ViewRecord) {
            toolbar.setTitle("View Record");
        } else if (fragment instanceof ViewReminderFragment) {
            toolbar.setTitle("View Reminder");
        }
    }

    @Override
    public void setFragment(Fragment fragment) {
        loadFragment(fragment);
    }

    @Override
    public void transferData(Profile profile) {
        user.setProfile(profile);
        databaseReference.setValue(user);
    }

    @Override
    public void transferData(Nutrition nutrition) {
        user.setNutrition(nutrition);
        databaseReference.setValue(user);
        loadFragment(new NutritionFragment());
    }

    @Override
    public void transferData(History history) {
        user.setHistory(history);
        databaseReference.setValue(user);
        loadFragment(new HealthRecordsFragment());
    }

    @Override
    public void transferData(ReminderList reminderList) {
        user.setReminderList(reminderList);
        databaseReference.setValue(user);
        loadFragment(new RemindersFragment());
    }

    @Override
    public void logout() {
        signOut();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof HomeFragment) {
            super.onBackPressed();
        } else if (currentFragment instanceof ViewRecord || currentFragment instanceof AddHealthRecordsFragment) {
            loadFragment(new HealthRecordsFragment());
        } else if (currentFragment instanceof ViewReminderFragment || currentFragment instanceof AddReminderFragment) {
            loadFragment(new RemindersFragment());
        } else {
            loadFragment(new HomeFragment());
        }
    }

    private void readData() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        AtomicInteger counter = new AtomicInteger();
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_MOVE_MINUTES, FitnessOptions.ACCESS_READ)
                .build();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        // Read Steps
        Fitness.getHistoryClient(getApplicationContext(), account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataSet -> {
                    int totalSteps = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
                    fitnessData.setSteps(totalSteps);
                    Log.d("fit_ness", "Cal: " + fitnessData.getCalories() + "Dist: " + fitnessData.getDistance() + "mins: " + fitnessData.getMoveMinutes() + "steps: " + fitnessData.getSteps());
                    counter.getAndIncrement();
                    if (counter.get() == 4) {
                        sendFitnessData(currentFragment);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("fit_ness", e.getMessage());
                });

        // Read Calories
        Fitness.getHistoryClient(getApplicationContext(), account)
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(dataSet -> {
                    float totalCalories = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();
                    fitnessData.setCalories(totalCalories);
                    Log.d("fit_ness", "Cal: " + fitnessData.getCalories() + "Dist: " + fitnessData.getDistance() + "mins: " + fitnessData.getMoveMinutes() + "steps: " + fitnessData.getSteps());
                    counter.getAndIncrement();
                    if (counter.get() == 4) {
                        sendFitnessData(currentFragment);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.d("fit_ness", e.getMessage());

                });

        // Read Distance
        Fitness.getHistoryClient(getApplicationContext(), account)
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(dataSet -> {
                    float totalDistance = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DISTANCE).asFloat();
                    fitnessData.setDistance(totalDistance);
                    Log.d("fit_ness", "Cal: " + fitnessData.getCalories() + "Dist: " + fitnessData.getDistance() + "mins: " + fitnessData.getMoveMinutes() + "steps: " + fitnessData.getSteps());
                    counter.getAndIncrement();
                    if (counter.get() == 4) {
                        sendFitnessData(currentFragment);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.d("fit_ness", e.getMessage());

                });

        // Read Move Minutes
        Fitness.getHistoryClient(getApplicationContext(), account)
                .readDailyTotal(DataType.TYPE_MOVE_MINUTES)
                .addOnSuccessListener(dataSet -> {
                    int totalMoveMinutes = dataSet.isEmpty()
                            ? 0
                            : dataSet.getDataPoints().get(0).getValue(Field.FIELD_DURATION).asInt();
                    fitnessData.setMoveMinutes(totalMoveMinutes);
                    Log.d("fit_ness", "Cal: " + fitnessData.getCalories() + "Dist: " + fitnessData.getDistance() + "mins: " + fitnessData.getMoveMinutes() + "steps: " + fitnessData.getSteps());
                    counter.getAndIncrement();
                    if (counter.get() == 4) {
                        sendFitnessData(currentFragment);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Log.d("fit_ness", e.getMessage());
                });

        Log.d("fit_ness", "Cal: " + fitnessData.getCalories() + "Dist: " + fitnessData.getDistance() + "mins: " + fitnessData.getMoveMinutes() + "steps: " + fitnessData.getSteps());
    }

    void sendFitnessData(Fragment fragment) {
        if (fragment instanceof HealthTrackingFragment) {
            ((HealthTrackingFragment) fragment).receiveData(fitnessData);
        }
    }


}