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

public class PlacementRecyclerAdapter extends RecyclerView.Adapter<PlacementRecyclerAdapter.MyViewHolder> {

    PlacementModelRoot placementModelRoot;
    Context context;

    public PlacementRecyclerAdapter(PlacementModelRoot placementModelRoot, Context context) {
        this.placementModelRoot = placementModelRoot;
        this.context = context;
    }

    @Override
    public PlacementRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.placement_recycler_layout,parent,false);
        return new PlacementRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacementRecyclerAdapter.MyViewHolder holder, int position) {

        holder.name.setText(placementModelRoot.placement_details.get(position).candidate_name);
        holder.placedVia.setText(placementModelRoot.placement_details.get(position).placed_through);
        holder.company.setText(placementModelRoot.placement_details.get(position).company_name);
        holder.position.setText(placementModelRoot.placement_details.get(position).position);
        holder.placedOn.setText(placementModelRoot.placement_details.get(position).placed_date);
        Glide.with(context).asBitmap().load(placementModelRoot.placement_details.get(position).picture).into(holder.candidateIv);

    }

    @Override
    public int getItemCount() {
        return placementModelRoot.placement_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,placedVia,company,position,placedOn;
        ImageView candidateIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.placement_name_tv);
            placedVia=itemView.findViewById(R.id.placement_placed_via_tv);
            company=itemView.findViewById(R.id.placement_company_tv);
            position=itemView.findViewById(R.id.placement_position_tv);
            placedOn=itemView.findViewById(R.id.placement_placed_dt);
            candidateIv=itemView.findViewById(R.id.placed_candidate_iv);

        }
    }
}
