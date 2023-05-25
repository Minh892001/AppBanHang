package com.example.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanhang.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private FloatingActionButton btnFab;
    private ViewPager viewPager;
    private FirebaseAuth auth;
    private Button btLogout;
    private FirebaseUser user;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btLogout = findViewById(R.id.btLogout);
        textView = findViewById(R.id.tvUser);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText("Hello " + user.getEmail());
        }

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView = findViewById(R.id.bottomNav);
        viewPager = findViewById(R.id.viewPager);
        btnFab = findViewById(R.id.fab);

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.navHome).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.navHistory).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.navSearch).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.navStatic).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navHistory:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navSearch:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navStatic:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }
}