package com.ksusha.vel.vodichka.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ksusha.vel.vodichka.Authentication;
import com.ksusha.vel.vodichka.MainActivity;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.ScaleLayoutManager;
import com.ksusha.vel.vodichka.adapter.TopRecyclerAdapter;
import com.ksusha.vel.vodichka.adapter.WaterAdapter;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.model.TopRecycler;
import com.ksusha.vel.vodichka.model.Water;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {

    RecyclerView waterRecycler;
    WaterAdapter waterAdapter;
    View view;
    Button buttonWater, buttonAxes, buttonCool;

    RecyclerView topRecycler;
    TopRecyclerAdapter topRecyclerAdapter;
    Timer timer;
    TimerTask timerTask;
    int position;
    ScaleLayoutManager layoutManager;
    ImageView imageAuthentication;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        DataForFragment.isBasket = false;

        setWaterRecycler(DataForFragment.waterList);

        buttonWater = view.findViewById(R.id.imageButtonWater);
        buttonAxes = view.findViewById(R.id.imageButtonAxes);
        buttonCool = view.findViewById(R.id.imageButtonCool);

        imageAuthentication = view.findViewById(R.id.imageAuthentication);
        buttonWater.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                waterRecycler.scrollToPosition(0);
                changeButtonWater();
            }
        });
        buttonAxes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                waterRecycler.scrollToPosition(3);
                changeButtonAxes();
            }
        });
        buttonCool.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                waterRecycler.scrollToPosition(6);
                changeButtonCool();
            }
        });

        setTopRecycler(DataForFragment.topRecyclerList);

        waterRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (DataForFragment.countBasket > 0) {
                    ((TextView) getActivity().findViewById(R.id.allCountBasket)).setText(Integer.toString(DataForFragment.countBasket));
                    ((ImageView) getActivity().findViewById(R.id.circleAllCountBasket)).setVisibility(View.VISIBLE);
                } else {
                    ((TextView) getActivity().findViewById(R.id.allCountBasket)).setText("");
                    ((ImageView)getActivity().findViewById(R.id.circleAllCountBasket)).setVisibility(View.GONE);
                }
            }
        });

        imageAuthentication.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAuth = new Intent(getContext(), Authentication.class);
                startActivity(intentAuth);
            }
        });

        return view;

    }

    private void changeButtonWater() {
        buttonWater.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
        buttonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category));
        buttonCool.setBackground(getResources().getDrawable(R.drawable.ic_category));
    }

    private void changeButtonAxes() {
        buttonWater.setBackground(getResources().getDrawable(R.drawable.ic_category));
        buttonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
        buttonCool.setBackground(getResources().getDrawable(R.drawable.ic_category));
    }

    private void changeButtonCool() {
        buttonWater.setBackground(getResources().getDrawable(R.drawable.ic_category));
        buttonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category));
        buttonCool.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
    }

    private void setWaterRecycler(List<Water> waterList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        waterRecycler = view.findViewById(R.id.waterRecycler);
        waterRecycler.setLayoutManager(layoutManager);

        waterAdapter = new WaterAdapter(waterList);
        waterRecycler.setAdapter(waterAdapter);

        waterRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) waterRecycler.getLayoutManager();
                if (lm.findFirstVisibleItemPosition() == 0) {
                    changeButtonWater();
                }
                if (lm.findFirstVisibleItemPosition() == 3) {
                    changeButtonAxes();
                }
                if (lm.findFirstVisibleItemPosition() == 6) {
                    changeButtonCool();
                }
            }
        });

    }

    private void setTopRecycler(List<TopRecycler> topRecyclerList) {

        layoutManager = new ScaleLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRecycler = view.findViewById(R.id.topRecycler);
        topRecycler.setLayoutManager(layoutManager);
        topRecyclerAdapter = new TopRecyclerAdapter(topRecyclerList);
        topRecycler.setAdapter(topRecyclerAdapter);


        if (topRecyclerList != null) {
            topRecycler.scrollToPosition(Integer.MAX_VALUE / 2);
        }


        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(topRecycler);
        topRecycler.smoothScrollBy(1000, 0);


        topRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 1) {
                    stopAutoScrollTopRecycler();
                } else if (newState == 0) {
                    position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollTopRecycler();
                }
            }
        });


    }

    private void runAutoScrollTopRecycler() {
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position != Integer.MAX_VALUE) {
                        position++;
                        topRecycler.smoothScrollToPosition(position);

                    } else {
                        topRecycler.scrollToPosition(Integer.MAX_VALUE / 2);
                        topRecycler.smoothScrollBy(1000, 0);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollTopRecycler();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollTopRecycler();
    }

    private void stopAutoScrollTopRecycler() {
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = layoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}