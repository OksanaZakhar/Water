package com.ksusha.vel.vodichka.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.adapter.WaterAdapter;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.model.Water;

import java.util.List;

public class StockFragment extends Fragment {

    RecyclerView waterRecycler;
    WaterAdapter waterAdapter;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stock, container, false);

        setWaterRecycler(DataForFragment.stockList);

        return view;

    }


    private void setWaterRecycler(List<Water> waterList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        waterRecycler = view.findViewById(R.id.waterRecycler);
        waterRecycler.setLayoutManager(layoutManager);

        waterAdapter = new WaterAdapter(waterList);
        waterRecycler.setAdapter(waterAdapter);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}