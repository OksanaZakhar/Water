package com.ksusha.vel.vodichka.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.model.TopRecycler;

import java.util.List;

public class TopRecyclerAdapter extends RecyclerView.Adapter<TopRecyclerAdapter.TopRecyclerViewHolder> {

    Context context;
    List<TopRecycler> topRecyclers;


    public TopRecyclerAdapter(List<TopRecycler> topRecyclers) {
        this.topRecyclers = topRecyclers;
    }

    @NonNull
    @Override
    public TopRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View topRecyclerItems = LayoutInflater.from(context).inflate(R.layout.top_recycler_item, parent, false);
        return new TopRecyclerViewHolder(topRecyclerItems);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerViewHolder holder, int position) {
        int actualPosition = position % topRecyclers.size();
        int imageId = context.getResources().getIdentifier(topRecyclers.get(actualPosition).getTopCardImage(), "drawable", context.getPackageName());
        holder.topCardImage.setImageResource(imageId);


        holder.topCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog_layout, null);

                ImageView imageView = view.findViewById(R.id.imageBottomSheet);
                int imageId = context.getResources().getIdentifier(topRecyclers.get(actualPosition).getTopCardImage(), "drawable", context.getPackageName());
                imageView.setImageResource(imageId);

                TextView textBottomTopic = view.findViewById(R.id.textBottomTopic);
                textBottomTopic.setText(topRecyclers.get(actualPosition).getTopic());

                TextView textBottomDescription = view.findViewById(R.id.textBottomDescription);
                textBottomDescription.setText(topRecyclers.get(actualPosition).getDescription());

                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static final class TopRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView topCardImage;

        public TopRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            topCardImage = itemView.findViewById(R.id.topCardImage);
        }
    }


}
