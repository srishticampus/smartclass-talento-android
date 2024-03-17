package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

public class SmartClassCourseRecyclerAdapter extends RecyclerView.Adapter<SmartClassCourseRecyclerAdapter.MyViewHolder> {

    TalentoTwoRoot courseModelRoot;
    Context context;

    public SmartClassCourseRecyclerAdapter(TalentoTwoRoot courseModelRoot, Context context) {
        this.courseModelRoot = courseModelRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public SmartClassCourseRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_layout_smartclass, parent, false);
        return new SmartClassCourseRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartClassCourseRecyclerAdapter.MyViewHolder holder, int position) {

//        try {
//            if (courseModelRoot.batch_details.get(position).batch_name.equals("Not Applicable")) {
//                courseModelRoot.batch_details.remove(position);
//                notifyItemRemoved(position);
//
//            }
//        } catch (Exception e) {
//
//        }


        holder.courseTv.setText(courseModelRoot.batch_details.get(position).batch_name);
        Glide.with(context).asBitmap().load(courseModelRoot.batch_details.get(position).image).into(holder.courseIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("courseId", courseModelRoot.batch_details.get(position).batch_id);
                editor.apply();

                Intent intent = new Intent(context, ExamDetailsActivity.class);
                intent.putExtra("courseIdExtra", courseModelRoot.batch_details.get(position).batch_id);
                //  intent.putExtra("category_name","srishti campus");
                intent.putExtra("fromCourse", "1");
                intent.putExtra("course_image", courseModelRoot.batch_details.get(position).image);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        LinearLayout rootLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTv = itemView.findViewById(R.id.course_name_tv_new);
            courseIv = itemView.findViewById(R.id.course_iv_new);
            rootLayout = itemView.findViewById(R.id.root_layout);
        }
    }
}
