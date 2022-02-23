package com.ksusha.vel.vodichka.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.adapter.WaterAdapter;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.model.Water;

import java.util.List;


public class BasketFragment extends Fragment {
    RecyclerView waterRecycler;
    WaterAdapter waterAdapter;
    TextView allSumBasket;
    View view;


    List<Water> basketList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);
        DataForFragment.setBasket(true);
        DataForFragment.initDataBasket();
        allSumBasket = view.findViewById(R.id.allSumBasket);

        setWaterRecycler(DataForFragment.basketList);

        allSumBasket.setText(Integer.toString(DataForFragment.sumBasket) + ",00 грн");

        waterRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (DataForFragment.countBasket > 0) {
                    ((TextView) getActivity().findViewById(R.id.allCountBasket)).setText(Integer.toString(DataForFragment.countBasket));
                } else {
                    ((TextView) getActivity().findViewById(R.id.allCountBasket)).setText("");
                }
                allSumBasket.setText(Integer.toString(DataForFragment.sumBasket) + ",00 грн");

            }
        });

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
    public void onDestroyView() {
        super.onDestroyView();
        DataForFragment.setBasket(false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}