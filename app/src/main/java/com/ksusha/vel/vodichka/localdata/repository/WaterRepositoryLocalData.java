package com.ksusha.vel.vodichka.localdata.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ksusha.vel.vodichka.localdata.WaterDAO;
import com.ksusha.vel.vodichka.localdata.WaterDataBase;
import com.ksusha.vel.vodichka.localdata.WaterEntity;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WaterRepositoryLocalData {

    private WaterDAO waterDAO;
    private LiveData<List<WaterEntity>> waters;

    public WaterRepositoryLocalData(Application application) {
        WaterDataBase dataBase = WaterDataBase.getInstance(application);
        waterDAO = dataBase.getWaterDAO();
    }


    public LiveData<List<WaterEntity>> getWaterList() {
        return waterDAO.getAllWaterList();
    }

    public WaterEntity getWaterEntity(long id) {
        return waterDAO.getWaterEntity(id);
    }


    public void insertWaterEntity(WaterEntity waterEntity) {

        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        waterDAO.insertWaterEntity(waterEntity);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public void updateWaterEntity(WaterEntity waterEntity) {

        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        waterDAO.updateWaterEntity(waterEntity);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public void deleteWaterEntity(WaterEntity waterEntity) {

        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        waterDAO.deleteWaterEntity(waterEntity);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public LiveData<List<WaterEntity>> getFilterList(String idWater) {
        return waterDAO.getFilterList(idWater);
    }

    public void addWaterEntityDB(WaterEntity waterEntity) {
        waterDAO.insertWaterEntity(waterEntity);

    }

}
