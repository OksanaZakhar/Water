package com.ksusha.vel.vodichka.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.data.DataForFragment;
import com.ksusha.vel.vodichka.fragments_page.MainWaterPage;
import com.ksusha.vel.vodichka.model.Water;

import java.util.List;

public class WaterAdapter extends RecyclerView.Adapter<WaterAdapter.WaterViewHolder> {

    Context context;
    List<Water> waters;
    public boolean masked;

    public WaterAdapter(List<Water> waters) {
        this.waters = waters;
    }

    @NonNull
    @Override
    public WaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View waterItems = LayoutInflater.from(context).inflate(R.layout.water_item, parent, false);

        return new WaterViewHolder(waterItems);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterViewHolder holder, int position) {

        int imageId = context.getResources().getIdentifier(waters.get(position).getImg(), "drawable", context.getPackageName());
        holder.waterImage.setImageResource(imageId);

        holder.waterDesc.setText(waters.get(position).getDescription());
        holder.waterPrise.setText(String.format("Ціна: %s,00грн", Integer.toString(waters.get(position).getPrise())));
        holder.waterCount.setText(Integer.toString(waters.get(position).getCount()));

        holder.gradient_ellipse.setClickable(waters.get(position).getMaskClicable());
        holder.gradient_ellipse.setVisibility((Integer) waters.get(position).getMaskVisible());
        holder.gradient_basket.setClickable(waters.get(position).getMaskClicable());
        holder.gradient_basket.setVisibility((Integer) waters.get(position).getMaskVisible());

        int sum = waters.get(position).getPrise() * waters.get(position).getCount();

        if (DataForFragment.isBasket) {
            holder.waterImage.setClickable(false);
            holder.waterSum.setText(String.format("Сума: %s,00грн", Integer.toString(sum)));
            holder.waterDelete.setClickable(true);
            holder.waterDelete.setVisibility(View.VISIBLE);

        } else {

            holder.waterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainWaterPage.class);
                    intent.putExtra("waterImage", imageId);
                    intent.putExtra("waterDesc", waters.get(position).getDescription());
                    intent.putExtra("waterPrice", String.format("Ціна: %s,00грн", Integer.toString(waters.get(position).getPrise())));
                    intent.putExtra("waterCount", Integer.toString(waters.get(position).getCount()));
                    intent.putExtra("waterId", waters.get(position).getId());

                    context.startActivity(intent);
                }
            });
        }

        holder.waterButtonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(waters.get(position).getId());
            }
        });

        holder.waterButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromCart(waters.get(position).getId());
                if (masked) {
                    holder.gradient_ellipse.setClickable(true);
                    holder.gradient_ellipse.setVisibility(View.VISIBLE);
                    holder.gradient_basket.setClickable(true);
                    holder.gradient_basket.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.waterDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = waters.get(position).getId();
                for (Water w : DataForFragment.waterList) {
                    if (w.getId().equals(id)) {
                        DataForFragment.countBasket = DataForFragment.countBasket - w.getCount();
                        DataForFragment.sumBasket = DataForFragment.sumBasket - w.getCount() * w.getPrise();
                        w.setCount(w.getStartCount());
                        w.setMaskClicable(true);
                        w.setMaskVisible(0);
                        masked = true;
                        DataForFragment.basketList.remove(w);
                    }
                }

                for (Water w : DataForFragment.stockList) {
                    if (w.getId().equals(id)) {
                        DataForFragment.countBasket = DataForFragment.countBasket - w.getCount();
                        DataForFragment.sumBasket = DataForFragment.sumBasket - w.getCount() * w.getPrise();
                        w.setCount(w.getStartCount());
                        w.setMaskClicable(true);
                        w.setMaskVisible(0);
                        masked = true;
                        DataForFragment.basketList.remove(w);
                    }
                }
                notifyDataSetChanged();

            }
        });

        holder.gradient_ellipse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Water w : DataForFragment.waterList) {
                    if (w.getId().equals(waters.get(position).getId())) {
                        DataForFragment.countBasket = DataForFragment.countBasket + w.getStartCount();
                        DataForFragment.sumBasket = DataForFragment.sumBasket + w.getStartCount() * w.getPrise();
                        w.setMaskClicable(false);
                        w.setMaskVisible(8);
                    }
                }
                for (Water w : DataForFragment.stockList) {
                    if (w.getId().equals(waters.get(position).getId())) {
                        DataForFragment.countBasket = DataForFragment.countBasket + w.getStartCount();
                        DataForFragment.sumBasket = DataForFragment.sumBasket + w.getStartCount() * w.getPrise();
                        w.setMaskClicable(false);
                        w.setMaskVisible(8);
                    }
                }
                holder.gradient_ellipse.setClickable(false);
                holder.gradient_ellipse.setVisibility(View.GONE);
                holder.gradient_basket.setClickable(false);
                holder.gradient_basket.setVisibility(View.GONE);
                masked = false;

            }
        });


    }

    public void addToCart(String id) {
        for (Water w : DataForFragment.waterList) {
            if (w.getId().equals(id)) {
                w.setCount(w.getCount() + 1);
                DataForFragment.countBasket = DataForFragment.countBasket + 1;
                DataForFragment.sumBasket = DataForFragment.sumBasket + w.getPrise();
            }
        }
        for (Water w : DataForFragment.stockList) {
            if (w.getId().equals(id)) {
                w.setCount(w.getCount() + 1);
                DataForFragment.countBasket = DataForFragment.countBasket + 1;
                DataForFragment.sumBasket = DataForFragment.sumBasket + w.getPrise();
            }
        }

        masked = false;

        notifyDataSetChanged();

    }

    public void removeFromCart(String id) {
        for (Water w : DataForFragment.waterList) {
            if (w.getId().equals(id)) {
                if (w.getCount() > w.getStartCount()) {
                    DataForFragment.countBasket = DataForFragment.countBasket - 1;
                    DataForFragment.sumBasket = DataForFragment.sumBasket - w.getPrise();
                    w.setCount(w.getCount() - 1);

                } else {
                    DataForFragment.countBasket = DataForFragment.countBasket - w.getStartCount();
                    DataForFragment.sumBasket = DataForFragment.sumBasket - w.getStartCount() * w.getPrise();
                    w.setMaskClicable(true);
                    w.setMaskVisible(0);
                    masked = true;
                    if (DataForFragment.isBasket) {
                        DataForFragment.basketList.remove(w);
                    }
                }
            }
        }
        for (Water w : DataForFragment.stockList) {
            if (w.getId().equals(id)) {
                if (w.getCount() > w.getStartCount()) {
                    DataForFragment.countBasket = DataForFragment.countBasket - 1;
                    DataForFragment.sumBasket = DataForFragment.sumBasket - w.getPrise();
                    w.setCount(w.getCount() - 1);
                } else {
                    DataForFragment.countBasket = DataForFragment.countBasket - w.getStartCount();
                    DataForFragment.sumBasket = DataForFragment.sumBasket - w.getStartCount() * w.getPrise();
                    w.setMaskClicable(true);
                    w.setMaskVisible(0);
                    masked = true;
                    if (DataForFragment.isBasket) {
                        DataForFragment.basketList.remove(w);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return waters.size();
    }

    public static final class WaterViewHolder extends RecyclerView.ViewHolder {


        ImageView waterImage, gradient_ellipse, gradient_basket;
        TextView waterDesc, waterPrise, waterCount, waterSum;
        Button waterButtonMinus, waterButtonPlus;
        ImageButton waterDelete;

        public WaterViewHolder(@NonNull View itemView) {
            super(itemView);

            waterImage = itemView.findViewById(R.id.waterImage);
            waterDesc = itemView.findViewById(R.id.waterDesc);
            waterPrise = itemView.findViewById(R.id.waterPrise);
            waterCount = itemView.findViewById(R.id.waterCount);


            waterButtonMinus = itemView.findViewById(R.id.waterButtonMinus);
            waterButtonPlus = itemView.findViewById(R.id.waterButtonPlus);

            gradient_ellipse = itemView.findViewById(R.id.gradient_ellipse);
            gradient_basket = itemView.findViewById(R.id.gradient_basket);

            waterSum = itemView.findViewById(R.id.waterSum);
            waterDelete = itemView.findViewById(R.id.waterDelete);

        }
    }

}


