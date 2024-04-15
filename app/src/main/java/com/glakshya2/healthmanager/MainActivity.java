package com.glakshya2.healthmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.glakshya2.healthmanager.nutrition.Nutrition;
import com.glakshya2.healthmanager.profile.Profile;
import com.glakshya2.healthmanager.records.HealthRecords;
import com.glakshya2.healthmanager.reminders.Reminders;
import com.glakshya2.healthmanager.tracking.HealthTracking;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ChildToHost {

    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        firebaseAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.inflateMenu(R.menu.nav_menu);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        if (menuItemId == R.id.records) {
            loadFragment(new HealthRecords());
            toolbar.setTitle("Health Records");
        } else if (menuItemId == R.id.tracking) {
            loadFragment(new HealthTracking());
            toolbar.setTitle("Health Tracking");
        } else if (menuItemId == R.id.nutrition) {
            loadFragment(new Nutrition());
            toolbar.setTitle("Nutrition");
        } else if (menuItemId == R.id.reminders) {
            loadFragment(new Reminders());
            toolbar.setTitle("Reminders");
        } else if (menuItemId == R.id.profile) {
            loadFragment(new Profile());
            toolbar.setTitle("Profile");
        } else if (menuItemId == R.id.logout) {
            signOut();
        }
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
    }

    @Override
    public void transferData(Fragment fragment) {
        loadFragment(fragment);
    }
}