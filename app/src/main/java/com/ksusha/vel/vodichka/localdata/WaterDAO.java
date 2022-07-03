package com.ksusha.vel.vodichka.localdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WaterDAO {

    @Query("SELECT * FROM water_table")
    LiveData<List<WaterEntity>> getAllWaterList();

    @Query("SELECT * FROM water_table WHERE id==:id")
    WaterEntity getWaterEntity(long id);

    @Insert
    void insertWaterEntity(WaterEntity waterEntity);

    @Update
    void updateWaterEntity(WaterEntity waterEntity);

    @Delete
    void deleteWaterEntity(WaterEntity waterEntity);

    @Query("SELECT * FROM water_table WHERE idWater==:idWater")
    LiveData<List<WaterEntity>> getFilterList(String idWater);

}
