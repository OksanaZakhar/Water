package com.ksusha.vel.vodichka.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.WaterItemBinding;
import com.ksusha.vel.vodichka.localdata.WaterEntity;
import com.ksusha.vel.vodichka.ui.view.MainWaterPage;
import com.ksusha.vel.vodichka.ui.view.RecyclerviewOnClickListener;

import java.util.List;

public class WaterAdapterMainFragment extends RecyclerView.Adapter<WaterAdapterMainFragment.WaterViewHolder> {

    Context context;
    List<WaterEntity> waters;
    RecyclerviewOnClickListener listener;

    public WaterAdapterMainFragment(RecyclerviewOnClickListener listener, List<WaterEntity> waters) {
        this.waters = waters;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        WaterItemBinding waterItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context),
                        R.layout.water_item, parent, false);

        return new WaterViewHolder(waterItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {

        int imageId = context.getResources().getIdentifier(waters.get(position).getImg(), "drawable", context.getPackageName());
        holder.waterItemBinding.waterImage.setImageResource(imageId);

        holder.waterItemBinding.waterDesc.setText(waters.get(position).getDescription());
        holder.waterItemBinding.waterPrise.setText(String.format("Ціна: %s,00грн", Integer.toString(waters.get(position).getPrise())));
        holder.waterItemBinding.waterCount.setText(Integer.toString(waters.get(position).getCount()));
        holder.waterItemBinding.gradientEllipse.setClickable(waters.get(position).getMaskClickable());
        holder.waterItemBinding.gradientEllipse.setVisibility((Integer) waters.get(position).getMaskVisible());
        holder.waterItemBinding.gradientBasket.setClickable(waters.get(position).getMaskClickable());
        holder.waterItemBinding.gradientBasket.setVisibility((Integer) waters.get(position).getMaskVisible());

        holder.waterItemBinding.gradientEllipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.recyclerviewClickDeleteMask(waters.get(position).getId());
            }
        });

        holder.waterItemBinding.waterButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.recyclerviewChangeCount(waters.get(position).getId(), waters.get(position).getCount() + 1);
            }
        });

        holder.waterItemBinding.waterButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waters.get(position).getCount() - 1 >= waters.get(position).getStartCount()) {
                    listener.recyclerviewChangeCount(waters.get(position).getId(), waters.get(position).getCount() - 1);
                }
                if (waters.get(position).getCount() == waters.get(position).getStartCount()) {
                    listener.recyclerviewClickAddMask(waters.get(position).getId());
                }
            }
        });

        holder.waterItemBinding.waterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainWaterPage.class);
                intent.putExtra("waterImage", imageId);
                intent.putExtra("waterDesc", waters.get(position).getDescription());
                intent.putExtra("waterPrice", String.format("Ціна: %s,00грн", Integer.toString(waters.get(position).getPrise())));
                intent.putExtra("waterCount", waters.get(position).getCount());
                intent.putExtra("waterStartCount", waters.get(position).getStartCount());
                intent.putExtra("waterId", waters.get(position).getId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return waters.size();
    }

    public static final class WaterViewHolder extends RecyclerView.ViewHolder {


        WaterItemBinding waterItemBinding;

        public WaterViewHolder(@NonNull WaterItemBinding waterItemBinding) {
            super(waterItemBinding.getRoot());
            this.waterItemBinding = waterItemBinding;
        }
    }

}


