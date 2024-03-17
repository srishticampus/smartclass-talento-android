package com.srishti.talento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.PlacementModel.PlacementModelRoot;

public class NewPlacementAdapter extends RecyclerView.Adapter<NewPlacementAdapter.MyViewHolder> {
    PlacementModelRoot placementModelRoot;
    Context context;
    RecyclerViewOnItemClick recyclerViewOnItemClick;

    public NewPlacementAdapter(PlacementModelRoot placementModelRoot, Context context, RecyclerViewOnItemClick recyclerViewOnItemClick) {
        this.placementModelRoot = placementModelRoot;
        this.context = context;
        this.recyclerViewOnItemClick = recyclerViewOnItemClick;
    }

    @Override
    public NewPlacementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.placement_recyclerview_new_layout, parent, false);
        return new NewPlacementAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewPlacementAdapter.MyViewHolder holder, int position) {

        holder.candidateName.setText(placementModelRoot.placement_details.get(position).candidate_name);
        Glide.with(context).asBitmap().load(placementModelRoot.placement_details.get(position).picture).into(holder.candidateIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewOnItemClick.recyclerViewItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return placementModelRoot.placement_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView candidateIv;
        TextView candidateName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            candidateName = itemView.findViewById(R.id.placed_candidate_name_tv);
            candidateIv = itemView.findViewById(R.id.placement_candidate_iv_new);

        }
    }
}
