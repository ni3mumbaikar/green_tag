package com.example.greentag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.greentag.fragment.Leaderboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentContainerView fragmentContainerView;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fragmentContainerView = findViewById(R.id.fragment_container_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Toast.makeText(HomeScreen.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_leaderboard:

                        item.setEnabled(true);
                        Toast.makeText(HomeScreen.this, "leader", Toast.LENGTH_SHORT).show();
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .add(R.id.fragment_container_view, Leaderboard.class, null)
                                    .commit();
                        }
                        return true;
                    case R.id.menu_near_me:
                        Toast.makeText(HomeScreen.this, "near me", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_profile:
                        Toast.makeText(HomeScreen.this, "profile", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Toast.makeText(HomeScreen.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.menu_leaderboard:
                        Toast.makeText(HomeScreen.this, "leader", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_near_me:
                        Toast.makeText(HomeScreen.this, "near me", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_profile:
                        Toast.makeText(HomeScreen.this, "profile", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}