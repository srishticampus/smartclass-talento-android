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
import com.srishti.talento.Home_Category_Model.CategoryRoot;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    CategoryRoot categoryRoot;
    Context context;

    public HomeRecyclerAdapter(CategoryRoot categoryRoot, Context context) {
        this.categoryRoot = categoryRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.MyViewHolder holder, int position) {
        holder.categoryTv.setText(categoryRoot.category_details.get(position).category_name);
        Glide.with(context).asBitmap().load(categoryRoot.category_details.get(position).image).into(holder.categoryIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category_name", categoryRoot.category_details.get(position).category_name);
                editor.apply();
                if (categoryRoot.category_details.get(position).category_name.equals("srishti campus")) {
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ExamDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRoot.category_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTv;
        ImageView categoryIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.home_recyclerview_tv);
            categoryIv = itemView.findViewById(R.id.home_recyclerview_iv);
        }
    }
}
