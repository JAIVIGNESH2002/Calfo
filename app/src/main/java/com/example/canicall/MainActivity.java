package com.example.canicall;

import android.os.Bundle;

import com.example.canicall.ui.main.fragTwo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.canicall.ui.main.SectionsPagerAdapter;
import com.example.canicall.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static List<userDetails> userDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        initData();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


    }
    private void initData() {
        userDetailsList = new ArrayList<>();
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User1", "At work"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User2", "At work"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User3", "Buzy"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User4", "Away"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User5", "Idle"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User6", "Buzy"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User7", "Work"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User8", "Buzy"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User9", "Travelling"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User10", "Buzy"));
        userDetailsList.add(new userDetails(R.drawable.ic_launcher_background, "User11", "Buzy"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.frag_two_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_user);
        SearchView searchView =(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Type to search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
