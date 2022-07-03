package com.ksusha.vel.vodichka.ui.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.ActivityWaterPageBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.viewmodel.MainActivityViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainWaterPage extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    ActivityWaterPageBinding activityWaterPageBinding;
    public static boolean toBasketGo;
    long id;
    int count, startCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityWaterPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_water_page);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        toBasketGo = false;

        id = getIntent().getLongExtra("waterId", 0);
        activityWaterPageBinding.waterPageImage.setImageResource(getIntent().getIntExtra("waterImage", 0));
        activityWaterPageBinding.waterDescWaterPage.setText(getIntent().getStringExtra("waterDesc"));
        activityWaterPageBinding.priceWaterPage.setText(getIntent().getStringExtra("waterPrice"));
        count = getIntent().getIntExtra("waterCount", 0);
        startCount = getIntent().getIntExtra("waterStartCount", 0);
        activityWaterPageBinding.countWaterPage.setText("" + count);

        activityWaterPageBinding.waterButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count - 1 >= startCount) {

                    Observable.create(new ObservableOnSubscribe<Object>() {
                                @Override
                                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                                    WaterEntity waterEntity = mainActivityViewModel.getWaterEntity(id);
                                    waterEntity.setCount(waterEntity.getCount() - 1);
                                    mainActivityViewModel.updateWaterEntity(waterEntity);

                                }
                            }).subscribeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                    count--;
                    activityWaterPageBinding.countWaterPage.setText("" + count);
                }

                if (count == startCount) {
                    Observable.create(new ObservableOnSubscribe<Object>() {
                                @Override
                                public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                                    WaterEntity waterEntity = mainActivityViewModel.getWaterEntity(id);
                                    waterEntity.setMaskClickable(true);
                                    waterEntity.setMaskVisible(View.VISIBLE);
                                    mainActivityViewModel.updateWaterEntity(waterEntity);

                                }
                            }).subscribeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }


            }
        });

        activityWaterPageBinding.waterButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                                WaterEntity waterEntity = mainActivityViewModel.getWaterEntity(id);
                                waterEntity.setCount(waterEntity.getCount() + 1);
                                mainActivityViewModel.updateWaterEntity(waterEntity);
                            }
                        }).subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe();

                count++;
                activityWaterPageBinding.countWaterPage.setText("" + count);
            }
        });


        activityWaterPageBinding.buttonHomeWaterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(view);
            }
        });
        activityWaterPageBinding.buttonBasketWaterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBasket(view);
            }
        });
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToBasket(View view) {
        toBasketGo = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}