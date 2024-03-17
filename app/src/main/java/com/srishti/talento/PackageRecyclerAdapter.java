package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.PackageModel.PackageRoot;

public class PackageRecyclerAdapter extends RecyclerView.Adapter<PackageRecyclerAdapter.MyViewHolder> {

    PackageRoot packageRoot;
    Context context;

    public PackageRecyclerAdapter(PackageRoot packageRoot, Context context) {
        this.packageRoot = packageRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public PackageRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_recycler_layout, parent, false);
        return new PackageRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageRecyclerAdapter.MyViewHolder holder, int position) {
        holder.packageTypeTv.setText(packageRoot.package_details.get(position).type_name);
        Glide.with(context).asBitmap().load(packageRoot.package_details.get(position).image).into(holder.packageTypeIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("typeName", packageRoot.package_details.get(position).type_name);
                editor.apply();

                Intent intent = new Intent(context.getApplicationContext(), ExamDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packageRoot.package_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView packageTypeTv;
        ImageView packageTypeIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            packageTypeTv = itemView.findViewById(R.id.package_recyclerview_tv);
            packageTypeIv = itemView.findViewById(R.id.package_recyclerview_iv);
        }
    }
}
