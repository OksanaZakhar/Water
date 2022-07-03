package com.ksusha.vel.vodichka.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.databinding.FragmentMainBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.adapter.TopRecyclerAdapter;
import com.ksusha.vel.vodichka.ui.adapter.WaterAdapterMainFragment;
import com.ksusha.vel.vodichka.ui.model.TopRecycler;
import com.ksusha.vel.vodichka.ui.view.Authentication;
import com.ksusha.vel.vodichka.ui.view.RecyclerviewOnClickListener;
import com.ksusha.vel.vodichka.ui.view.ScaleLayoutManager;
import com.ksusha.vel.vodichka.ui.viewmodel.MainActivityViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainFragment extends Fragment implements RecyclerviewOnClickListener {

    WaterAdapterMainFragment waterAdapter;
    TopRecyclerAdapter topRecyclerAdapter;
    List<WaterEntity> waters;
    Timer timer;
    TimerTask timerTask;
    int position;
    int topPosition = 0;
    ScaleLayoutManager layoutManager;
    MainActivityViewModel mainActivityViewModel;
    FragmentMainBinding fragmentMainBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main, container, false);
        View view = fragmentMainBinding.getRoot();

        mainActivityViewModel = new ViewModelProvider(requireActivity())
                .get(MainActivityViewModel.class);

        mainActivityViewModel.getFilterList("mf").observe(getActivity(),
                new Observer<List<WaterEntity>>() {
                    @Override
                    public void onChanged(List<WaterEntity> waterEntities) {
                        waters = (List<WaterEntity>) waterEntities;
                        setWaterRecycler(waters);
                        fragmentMainBinding.waterRecycler.scrollToPosition(topPosition);
                    }
                });

        fragmentMainBinding.imageButtonWater.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMainBinding.waterRecycler.scrollToPosition(0);
                changeButtonWater();
            }
        });
        fragmentMainBinding.imageButtonAxes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMainBinding.waterRecycler.scrollToPosition(3);
                changeButtonAxes();
            }
        });
        fragmentMainBinding.imageButtonCool.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentMainBinding.waterRecycler.scrollToPosition(6);
                changeButtonCool();
            }
        });

        setTopRecycler(DataForFragment.topRecyclerList);

        fragmentMainBinding.imageAuthentication.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAuth = new Intent(getContext(), Authentication.class);
                startActivity(intentAuth);
            }
        });

        return view;

    }

    private void changeButtonWater() {
        fragmentMainBinding.imageButtonWater.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
        fragmentMainBinding.imageButtonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category));
        fragmentMainBinding.imageButtonCool.setBackground(getResources().getDrawable(R.drawable.ic_category));
    }

    private void changeButtonAxes() {
        fragmentMainBinding.imageButtonWater.setBackground(getResources().getDrawable(R.drawable.ic_category));
        fragmentMainBinding.imageButtonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
        fragmentMainBinding.imageButtonCool.setBackground(getResources().getDrawable(R.drawable.ic_category));
    }

    private void changeButtonCool() {
        fragmentMainBinding.imageButtonWater.setBackground(getResources().getDrawable(R.drawable.ic_category));
        fragmentMainBinding.imageButtonAxes.setBackground(getResources().getDrawable(R.drawable.ic_category));
        fragmentMainBinding.imageButtonCool.setBackground(getResources().getDrawable(R.drawable.ic_category_blue));
    }

    private void setWaterRecycler(List<WaterEntity> waterList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        fragmentMainBinding.waterRecycler.setLayoutManager(layoutManager);
        waterAdapter = new WaterAdapterMainFragment(this, waterList);
        fragmentMainBinding.waterRecycler.setAdapter(waterAdapter);
        fragmentMainBinding.waterRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) fragmentMainBinding.waterRecycler.getLayoutManager();
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

    @Override
    public void recyclerviewClickDeleteMask(long id) {

        topPosition = ((LinearLayoutManager) fragmentMainBinding.waterRecycler.getLayoutManager()).findFirstVisibleItemPosition();

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

        topPosition = ((LinearLayoutManager) fragmentMainBinding.waterRecycler.getLayoutManager()).findFirstVisibleItemPosition();

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

        topPosition = ((LinearLayoutManager) fragmentMainBinding.waterRecycler.getLayoutManager()).findFirstVisibleItemPosition();

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


    private void setTopRecycler(List<TopRecycler> topRecyclerList) {

        layoutManager = new ScaleLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        fragmentMainBinding.topRecycler.setLayoutManager(layoutManager);
        topRecyclerAdapter = new TopRecyclerAdapter(topRecyclerList);
        fragmentMainBinding.topRecycler.setAdapter(topRecyclerAdapter);

        if (topRecyclerList != null) {
            fragmentMainBinding.topRecycler.scrollToPosition(Integer.MAX_VALUE / 2);
        }
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(fragmentMainBinding.topRecycler);
        fragmentMainBinding.topRecycler.smoothScrollBy(1000, 0);

        fragmentMainBinding.topRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        fragmentMainBinding.topRecycler.smoothScrollToPosition(position);

                    } else {
                        fragmentMainBinding.topRecycler.scrollToPosition(Integer.MAX_VALUE / 2);
                        fragmentMainBinding.topRecycler.smoothScrollBy(1000, 0);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
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
    public void onResume() {
        super.onResume();
        runAutoScrollTopRecycler();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollTopRecycler();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}