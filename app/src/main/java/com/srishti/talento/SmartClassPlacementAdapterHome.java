package com.srishti.talento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.PlacementModel.PlacementModelRoot;

public class SmartClassPlacementAdapterHome extends RecyclerView.Adapter<SmartClassPlacementAdapterHome.MyViewHolder> {
    PlacementModelRoot placementModelRoot;
    Context context;

    public SmartClassPlacementAdapterHome(PlacementModelRoot placementModelRoot, Context context) {
        this.placementModelRoot = placementModelRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public SmartClassPlacementAdapterHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_placement_recycler_layout, parent, false);
        return new SmartClassPlacementAdapterHome.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartClassPlacementAdapterHome.MyViewHolder holder, int position) {

        Glide.with(context).asBitmap().load(placementModelRoot.placement_details.get(position).picture).into(holder.placedIv);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView placedIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            placedIv = itemView.findViewById(R.id.placement_candidate_iv_home);
        }
    }
}
