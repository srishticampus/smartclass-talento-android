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
import com.srishti.talento.CourseModel.CourseModelRoot;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.MyViewHolder> {

    CourseModelRoot courseModelRoot;
    Context context;

    public CourseRecyclerAdapter(CourseModelRoot courseModelRoot, Context context) {
        this.courseModelRoot = courseModelRoot;
        this.context = context;
    }

    @Override
    public CourseRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_layout,parent,false);
        return new CourseRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.MyViewHolder holder, int position) {
        holder.courseTv.setText(courseModelRoot.batch_details.get(position).batch_name);
        Glide.with(context).asBitmap().load(courseModelRoot.batch_details.get(position).image).into(holder.courseIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("courseId",courseModelRoot.batch_details.get(position).batch_id);
                editor.apply();

                Intent intent = new Intent(context, ExamDetailsActivity.class);
                intent.putExtra("courseIdExtra",courseModelRoot.batch_details.get(position).batch_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseModelRoot.batch_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseTv;
        ImageView courseIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            courseTv=itemView.findViewById(R.id.course_tv);
            courseIv=itemView.findViewById(R.id.course_iv);
        }
    }
}
