package com.ksusha.vel.vodichka;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.fragment.BasketFragment;
import com.ksusha.vel.vodichka.fragment.MainFragment;
import com.ksusha.vel.vodichka.fragment.StockFragment;
import com.ksusha.vel.vodichka.fragments_page.MainWaterPage;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ImageButton buttonHistory, openDrawer;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonHistory = findViewById(R.id.buttonHistory);
        buttonHistory.setOnClickListener(this);

        if (DataForFragment.waterList.size() == 0) {
            DataForFragment.initData();
        }

        if (MainWaterPage.toBasketGo) {
            Fragment fragment = new BasketFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragMain, fragment);
            fragmentTransaction.commit();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        openDrawer = findViewById(R.id.btn_open);

        navigationView.setNavigationItemSelectedListener(this);

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
                drawerLayout.openDrawer(GravityCompat.END);

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.oneMenu:
                Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
                break;
            case R.id.twoMenu:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            case R.id.threeMenu:
                Toast.makeText(MainActivity.this, "Three", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fourMenu:
                Toast.makeText(MainActivity.this, "Four", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fiveMenu:
                Toast.makeText(MainActivity.this, "Five", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sixMenu:
                Toast.makeText(MainActivity.this, "Six", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void Change(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.buttonBasket:
                fragment = new BasketFragment();
                break;
            case R.id.buttonStock:
                fragment = new StockFragment();
                break;
            case R.id.buttonMain:
                fragment = new MainFragment();
                break;

            default:
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragMain, fragment);
        fragmentTransaction.commit();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonHistory:
                Intent intent0 = new Intent(this, HistoryActivity.class);
                startActivity(intent0);
                break;

            default:
                break;
        }

    }
}