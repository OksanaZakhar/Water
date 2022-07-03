package com.ksusha.vel.vodichka.localdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WaterEntity.class}, version = 1, exportSchema = false)
public abstract class WaterDataBase extends RoomDatabase {

    public abstract WaterDAO getWaterDAO();

    private static WaterDataBase instance;

    public static synchronized WaterDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            WaterDataBase.class,
                            "water_basket_table"
                    ).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
