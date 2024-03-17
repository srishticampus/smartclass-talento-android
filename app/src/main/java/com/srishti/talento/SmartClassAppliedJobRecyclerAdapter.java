package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.AppliedJobModel.AppliedJobModelRoot;

public class SmartClassAppliedJobRecyclerAdapter extends RecyclerView.Adapter<SmartClassAppliedJobRecyclerAdapter.MyViewHolder> {

    AppliedJobModelRoot appliedJobModelRoot;
    Context context;

    public SmartClassAppliedJobRecyclerAdapter(AppliedJobModelRoot appliedJobModelRoot, Context context) {
        this.appliedJobModelRoot = appliedJobModelRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public SmartClassAppliedJobRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.applied_job_recycler_layout, parent, false);
        return new SmartClassAppliedJobRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartClassAppliedJobRecyclerAdapter.MyViewHolder holder, int position) {
        holder.appliedJobTitle.setText(appliedJobModelRoot.user_jobdetails.get(position).job_title);
        holder.companyNameFirstLetter.setText(appliedJobModelRoot.user_jobdetails.get(position).job_title.substring(0, 1).toUpperCase());
        if (position % 2 == 0) {
            holder.appliedjobRl.setBackgroundResource(R.drawable.squircle_color_bg_coral);
        } else {
            holder.appliedjobRl.setBackgroundResource(R.drawable.squircle_color_bg_light_green);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context.getApplicationContext(),SmartClassAppliedJobDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("company_name", appliedJobModelRoot.user_jobdetails.get(position).company);
                intent.putExtra("job_title", appliedJobModelRoot.user_jobdetails.get(position).job_title);
                intent.putExtra("qualification", appliedJobModelRoot.user_jobdetails.get(position).qualification);
                intent.putExtra("job_description", appliedJobModelRoot.user_jobdetails.get(position).job_description);
                intent.putExtra("prefered_skill",appliedJobModelRoot.user_jobdetails.get(position).prefered_skill);
                intent.putExtra("job_location", appliedJobModelRoot.user_jobdetails.get(position).job_location);
                intent.putExtra("job_salary", appliedJobModelRoot.user_jobdetails.get(position).job_salary);
                intent.putExtra("start_date", appliedJobModelRoot.user_jobdetails.get(position).start_date);
                intent.putExtra("end_date", appliedJobModelRoot.user_jobdetails.get(position).end_date);
                intent.putExtra("jobId",appliedJobModelRoot.user_jobdetails.get(position).id);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedJobModelRoot.user_jobdetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appliedJobTitle, companyNameFirstLetter;
        RelativeLayout appliedjobRl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appliedJobTitle = itemView.findViewById(R.id.applied_job_title);
            companyNameFirstLetter = itemView.findViewById(R.id.company_name_first_letter);
            appliedjobRl = itemView.findViewById(R.id.rl_one);
        }
    }
}
