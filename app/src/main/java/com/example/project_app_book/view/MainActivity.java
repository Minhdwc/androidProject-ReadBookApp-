package com.example.project_app_book.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_book.R;
import com.example.project_app_book.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private User user;
    androidx.appcompat.widget.Toolbar actionBar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userJson = getIntent().getStringExtra("user");
        if (userJson != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, User.class);
        }

        addControls();
        addEvents();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    void addControls() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameFragment = findViewById(R.id.frameFragment);
    }

    void addEvents() {
        loadFragment(new FragmentHome());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.btnHome) {
                    loadFragment(new FragmentHome());
                    return true;
                }
                if (item.getItemId() == R.id.btnCompass) {
                    loadFragment(new FragmentHome());
                    return true;
                }
                if (item.getItemId() == R.id.btnUser) {
                    loadFragment(new FragmentUser(user));  // Pass the user object
                    return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragLayoutLoad, fragment);
        ft.commit();
    }
}
