package com.ksusha.vel.vodichka.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.FragmentBasketBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.adapter.WaterAdapterBasketFragment;
import com.ksusha.vel.vodichka.ui.view.RecyclerviewOnClickListener;
import com.ksusha.vel.vodichka.ui.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class BasketFragment extends Fragment implements RecyclerviewOnClickListener {

    WaterAdapterBasketFragment waterAdapter;
    String[] addresses = {"ksusha.vel.andrst@gmail.com"};
    String subject = "Заказ воды";
    List<WaterEntity> waters;
    List<WaterEntity> waterBasket;
    MainActivityViewModel mainActivityViewModel;
    FragmentBasketBinding fragmentBasketBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBasketBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_basket, container, false);

        View view = fragmentBasketBinding.getRoot();
        mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        mainActivityViewModel.getWaterList().observe(getActivity(),
                new Observer<List<WaterEntity>>() {
                    @Override
                    public void onChanged(List<WaterEntity> waterEntities) {
                        waters = (List<WaterEntity>) waterEntities;
                        waterBasket = new ArrayList<>();
                        int sumBasket = 0;
                        for (WaterEntity water : waters) {
                            if (water.getMaskVisible() == View.GONE) {
                                waterBasket.add(water);
                                sumBasket = sumBasket + water.getCount() * water.getPrise();
                            }
                        }
                        setWaterRecycler(waterBasket);
                        fragmentBasketBinding.allSumBasket.setText(sumBasket + ",00 грн");
                        if (sumBasket > 0) {
                            fragmentBasketBinding.orderDeal.setEnabled(true);
                        } else {
                            fragmentBasketBinding.orderDeal.setEnabled(false);
                        }
                    }
                });


        fragmentBasketBinding.orderDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });

        return view;
    }

    private void setWaterRecycler(List<WaterEntity> waterList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fragmentBasketBinding.waterRecycler.setLayoutManager(layoutManager);
        waterAdapter = new WaterAdapterBasketFragment(this, waterList);
        fragmentBasketBinding.waterRecycler.setAdapter(waterAdapter);
    }


    private void sendOrder() {
        int sum = 0;
        String order = "";
        order = order + "\n" + "\n";
        for (WaterEntity w : waterBasket) {
            order = order + " " +
                    w.getDescription() + " - " +
                    "Ціна - " + w.getPrise() + " " +
                    "Кількість - " + w.getCount() + " " +
                    "Сума - " + w.getPrise() * w.getCount() + ";" + "\n";
            sum = sum + w.getPrise() * w.getCount();
        }

        order = order + "Загальна сума замовлення - " + sum + ",00 грн";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Couldn't find an email app and account", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void recyclerviewClickDeleteMask(long id) {

        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        WaterEntity waterEntity = mainActivityViewModel.getWaterEntity(id);
                        waterEntity.setMaskClickable(false);
                        waterEntity.setMaskVisible(View.GONE);
                        mainActivityViewModel.updateWaterEntity(waterEntity);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    @Override
    public void recyclerviewClickAddMask(long id) {

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

    @Override
    public void recyclerviewChangeCount(long id, int count) {

        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        WaterEntity waterEntity = mainActivityViewModel.getWaterEntity(id);
                        waterEntity.setCount(count);
                        mainActivityViewModel.updateWaterEntity(waterEntity);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


}