package com.ksusha.vel.vodichka.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.localdata.repository.WaterRepositoryLocalData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    WaterRepositoryLocalData waterRepositoryLocalData;
    LiveData<List<WaterEntity>> waterList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        waterRepositoryLocalData = new WaterRepositoryLocalData(application);
    }


    public LiveData<List<WaterEntity>> getWaterList() {
        waterList = waterRepositoryLocalData.getWaterList();
        return waterList;
    }

    public WaterEntity getWaterEntity(long id) {
        return waterRepositoryLocalData.getWaterEntity(id);
    }

    public void insertWaterEntity(WaterEntity waterEntity) {
        waterRepositoryLocalData.insertWaterEntity(waterEntity);
    }

    public void updateWaterEntity(WaterEntity waterEntity) {
        waterRepositoryLocalData.updateWaterEntity(waterEntity);
    }

    public void deleteWaterEntity(WaterEntity waterEntity) {
        waterRepositoryLocalData.deleteWaterEntity(waterEntity);
    }

    public LiveData<List<WaterEntity>> getFilterList(String idWater) {
        waterList = waterRepositoryLocalData.getFilterList(idWater);
        return waterList;
    }

    public void addWaterEntityDB(WaterEntity waterEntity) {
        waterRepositoryLocalData.addWaterEntityDB(waterEntity);
    }

}
