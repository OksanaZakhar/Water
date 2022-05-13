package com.ksusha.vel.vodichka.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.adapter.WaterAdapter;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.model.Water;

import java.util.ArrayList;
import java.util.List;


public class BasketFragment extends Fragment {
    RecyclerView waterRecycler;
    WaterAdapter waterAdapter;
    TextView allSumBasket;
    Button orderDeal;
    View view;

    String[] addresses = {"ksusha.vel.andrst@gmail.com"};
    String subject = "Заказ воды";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basket, container, false);
        DataForFragment.setBasket(true);
        DataForFragment.initDataBasket();
        allSumBasket = view.findViewById(R.id.allSumBasket);

        orderDeal = view.findViewById(R.id.orderDeal);

        if (DataForFragment.countBasket > 0) {
            orderDeal.setEnabled(true);
        } else {
            orderDeal.setEnabled(false);
        }

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
                    ((ImageView) getActivity().findViewById(R.id.circleAllCountBasket)).setVisibility(View.VISIBLE);
                    orderDeal.setEnabled(true);
                } else {
                    ((TextView) getActivity().findViewById(R.id.allCountBasket)).setText("");
                    ((ImageView) getActivity().findViewById(R.id.circleAllCountBasket)).setVisibility(View.GONE);
                    orderDeal.setEnabled(false);
                }
                allSumBasket.setText(Integer.toString(DataForFragment.sumBasket) + ",00 грн");

            }
        });

        orderDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String order = "";
                List<Water> basketList = new ArrayList<>();
                basketList.addAll(DataForFragment.basketList);
                order = order + "\n" + "\n";
                for (Water b : basketList) {
                    order = order + " " +
                            b.getDescription() + " - " +
                            "Ціна - " + b.getPrise() + " " +
                            "Кількість - " + b.getCount() + " " +
                            "Сума - " + b.getPrise() * b.getCount() + ";" + "\n";
                }
                order = order + "Загальна сума замовлення - " + DataForFragment.sumBasket + ",00 грн";

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, order);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }


                //Toast.makeText(getContext(), order, Toast.LENGTH_LONG).show();


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