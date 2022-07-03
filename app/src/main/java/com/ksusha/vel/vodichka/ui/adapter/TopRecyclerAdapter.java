package com.ksusha.vel.vodichka.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ksusha.vel.vodichka.R;
import com.ksusha.vel.vodichka.databinding.BottomSheetDialogLayoutBinding;
import com.ksusha.vel.vodichka.databinding.TopRecyclerItemBinding;
import com.ksusha.vel.vodichka.ui.model.TopRecycler;

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
        TopRecyclerItemBinding topRecyclerItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context),
                        R.layout.top_recycler_item, parent, false);


        return new TopRecyclerViewHolder(topRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerViewHolder holder, int position) {
        int actualPosition = position % topRecyclers.size();
        int imageId = context.getResources().getIdentifier(topRecyclers.get(actualPosition).getTopCardImage(), "drawable", context.getPackageName());
        holder.topRecyclerItemBinding.topCardImage.setImageResource(imageId);


        holder.topRecyclerItemBinding.topCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                BottomSheetDialogLayoutBinding bottomSheetDialogLayoutBinding = DataBindingUtil
                        .inflate(LayoutInflater.from(context),
                                R.layout.bottom_sheet_dialog_layout, null, false);
                View view = bottomSheetDialogLayoutBinding.getRoot();
                int imageId = context.getResources().getIdentifier(topRecyclers.get(actualPosition).getTopCardImage(), "drawable", context.getPackageName());
                bottomSheetDialogLayoutBinding.imageBottomSheet.setImageResource(imageId);
                bottomSheetDialogLayoutBinding.textBottomTopic.setText(topRecyclers.get(actualPosition).getTopic());
                bottomSheetDialogLayoutBinding.textBottomDescription.setText(topRecyclers.get(actualPosition).getDescription());

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

        TopRecyclerItemBinding topRecyclerItemBinding;


        public TopRecyclerViewHolder(@NonNull TopRecyclerItemBinding topRecyclerItemBinding) {
            super(topRecyclerItemBinding.getRoot());
            this.topRecyclerItemBinding = topRecyclerItemBinding;
        }
    }
}
