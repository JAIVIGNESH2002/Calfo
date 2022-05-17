package com.example.canicall;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        SharedPreferences oneTimeAct = getSharedPreferences("checkTime",MODE_PRIVATE);
        String notFirstTime = oneTimeAct.getString("NotFirstRun","default");
        if(notFirstTime.equals("yes")){
            setContentView(binding.getRoot());
        }else{
            SharedPreferences.Editor editr = oneTimeAct.edit();
            editr.putString("NotFirstRun","yes");
            editr.apply();
            Intent getDetails = new Intent(MainActivity.this,getDet.class);
            startActivity(getDetails);
            finishAfterTransition();

        }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
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
