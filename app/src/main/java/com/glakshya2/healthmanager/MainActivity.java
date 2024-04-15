package com.glakshya2.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.glakshya2.healthmanager.home.HomeFragment;
import com.glakshya2.healthmanager.nutrition.AddNutritionFragment;
import com.glakshya2.healthmanager.nutrition.NutritionFragment;
import com.glakshya2.healthmanager.profile.ProfileFragment;
import com.glakshya2.healthmanager.records.AddHealthRecordsFragment;
import com.glakshya2.healthmanager.records.HealthRecordsFragment;
import com.glakshya2.healthmanager.reminders.AddReminderFragment;
import com.glakshya2.healthmanager.reminders.RemindersFragment;
import com.glakshya2.healthmanager.schema.Profile;
import com.glakshya2.healthmanager.schema.User;
import com.glakshya2.healthmanager.tracking.HealthTrackingFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        user = new User();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if (user == null) {
                    user = new User();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
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
        } else if (menuItemId == R.id.logout) {
            signOut();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        firebaseAuth.signOut();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, googleSignInOptions).revokeAccess()
                .addOnCompleteListener(this, task -> startActivity(new Intent(this, SignIn.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

    void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
        if (fragment instanceof HomeFragment) {
            toolbar.setTitle("Home");
        } else if (fragment instanceof AddNutritionFragment) {
            toolbar.setTitle("Add Meal");
        } else if (fragment instanceof NutritionFragment) {
            toolbar.setTitle("Nutrition");
        } else if (fragment instanceof ProfileFragment) {
            toolbar.setTitle("Profile");
            ProfileFragment profileFragment = (ProfileFragment) fragment;
            if (user != null && profileFragment != null) {
                profileFragment.receiveData(user.getProfile());
            }
        } else if (fragment instanceof AddHealthRecordsFragment) {
            toolbar.setTitle("Add Health Record");
        } else if (fragment instanceof HealthRecordsFragment) {
            toolbar.setTitle("Health Records");
        } else if (fragment instanceof AddReminderFragment) {
            toolbar.setTitle("Add Reminder");
        } else if (fragment instanceof RemindersFragment) {
            toolbar.setTitle("Reminders");
        } else if (fragment instanceof HealthTrackingFragment) {
            toolbar.setTitle("Health Tracking");
        }
    }

    @Override
    public void transferData(Fragment fragment) {
        loadFragment(fragment);
    }

    @Override
    public void transferData(Profile profile) {
        user.setProfile(profile);
        databaseReference.setValue(user);
    }
}