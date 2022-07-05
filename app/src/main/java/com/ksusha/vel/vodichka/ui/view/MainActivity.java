package com.ksusha.vel.vodichka.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.databinding.ActivityMainBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.fragment.BasketFragment;
import com.ksusha.vel.vodichka.ui.fragment.MainFragment;
import com.ksusha.vel.vodichka.ui.fragment.StockFragment;
import com.ksusha.vel.vodichka.ui.model.Water;
import com.ksusha.vel.vodichka.ui.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    MainActivityViewModel mainActivityViewModel;
    List<WaterEntity> waters;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        mainActivityViewModel.getWaterList().observe(this, new Observer<List<WaterEntity>>() {
            @Override
            public void onChanged(List<WaterEntity> waterEntities) {
                if (waterEntities.size() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            for (Water w : DataForFragment.waterListFromAPI) {
                                mainActivityViewModel.addWaterEntityDB(new WaterEntity(
                                        0,
                                        w.getId(),
                                        w.getImg(),
                                        w.getDescription(),
                                        w.getPrise(),
                                        w.getCount(),
                                        w.getStartCount(),
                                        w.getMaskClicable(),
                                        w.getMaskVisible()
                                ));
                            }
                        }
                    }).start();
                }
            }
        });

   
        // это мы из данных апи переписываем в БД ВСЕ!!!!

        activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"));
        activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"));
        activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"));
        activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"));
        activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#005080"));
        activityMainBinding.txtMain.setTextColor(Color.parseColor("#005080"));


        if (MainWaterPage.toBasketGo) {
            Fragment fragment = new BasketFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setReorderingAllowed(true);
            fragmentTransaction.replace(R.id.fragMain, fragment);
            fragmentTransaction.commit();
        }

        activityMainBinding.navigationView.setNavigationItemSelectedListener(this);

        activityMainBinding.buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    activityMainBinding.drawerLayout.closeDrawer(GravityCompat.END);
                }
                activityMainBinding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        mainActivityViewModel.getWaterList().observe(this,
                new Observer<List<WaterEntity>>() {
                    @Override
                    public void onChanged(List<WaterEntity> waterEntities) {
                        waters = (List<WaterEntity>) waterEntities;

                        List<WaterEntity> waterBasket = new ArrayList<>();
                        int count = 0;
                        for (WaterEntity water : waters) {
                            if (water.getMaskVisible() == View.GONE) {
                                waterBasket.add(water);
                                count = count + water.getCount();
                            }
                        }
                        if (count > 0) {
                            activityMainBinding.allCountBasket.setText(Integer.toString(count));
                            activityMainBinding.circleAllCountBasket.setVisibility(View.VISIBLE);
                        } else {
                            activityMainBinding.allCountBasket.setText("");
                            activityMainBinding.circleAllCountBasket.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.oneMenu:
                Intent intentAuth = new Intent(MainActivity.this, Authentication.class);
                startActivity(intentAuth);
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
                Toast.makeText(MainActivity.this, "Інформація", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fourMenu:
                Toast.makeText(MainActivity.this, "Зв’яжіться з нами", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fiveMenu:
                Toast.makeText(MainActivity.this, "Оцініть нас", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sixMenu:
                Toast.makeText(MainActivity.this, "Скарга директору", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void сhange(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.buttonBasket:
                fragment = new BasketFragment();
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#005080"));
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#005080"));
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"));
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#686868"));
                break;
            case R.id.buttonStock:
                fragment = new StockFragment();
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"));
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#005080"));
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#005080"));
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#686868"));
                break;
            case R.id.buttonMain:
                fragment = new MainFragment();
                activityMainBinding.buttonBasket.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtBasket.setTextColor(Color.parseColor("#686868"));
                activityMainBinding.buttonStock.setColorFilter(Color.parseColor("#686868"));
                activityMainBinding.txtStock.setTextColor(Color.parseColor("#686868"));
                activityMainBinding.buttonMain.setColorFilter(Color.parseColor("#005080"));
                activityMainBinding.txtMain.setTextColor(Color.parseColor("#005080"));
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
