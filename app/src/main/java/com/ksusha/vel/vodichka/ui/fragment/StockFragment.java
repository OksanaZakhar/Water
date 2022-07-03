package com.ksusha.vel.vodichka.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.FragmentStockBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.adapter.WaterAdapterStockFragment;
import com.ksusha.vel.vodichka.ui.view.RecyclerviewOnClickListener;
import com.ksusha.vel.vodichka.ui.viewmodel.MainActivityViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StockFragment extends Fragment implements RecyclerviewOnClickListener {

    WaterAdapterStockFragment waterAdapter;
    MainActivityViewModel mainActivityViewModel;
    List<WaterEntity> waters;
    FragmentStockBinding fragmentStockBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentStockBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_stock, container, false);
        View view = fragmentStockBinding.getRoot();

        mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        mainActivityViewModel.getFilterList("sf").observe(getActivity(),
                new Observer<List<WaterEntity>>() {
                    @Override
                    public void onChanged(List<WaterEntity> waterEntities) {
                        waters = (List<WaterEntity>) waterEntities;
                        setWaterRecycler(waters);
                    }
                });

        return view;

    }


    private void setWaterRecycler(List<WaterEntity> waterList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        fragmentStockBinding.waterRecycler.setLayoutManager(layoutManager);
        waterAdapter = new WaterAdapterStockFragment(this, waterList);
        fragmentStockBinding.waterRecycler.setAdapter(waterAdapter);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}