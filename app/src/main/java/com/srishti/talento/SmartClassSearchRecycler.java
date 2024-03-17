package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.JobAlertModel.JobAlertModelRoot;

public class SmartClassSearchRecycler extends RecyclerView.Adapter<SmartClassSearchRecycler.MyViewHolder> {

    JobAlertModelRoot jobAlertModelRoot;
    Context context;

    public SmartClassSearchRecycler(JobAlertModelRoot jobAlertModelRoot, Context context) {
        this.jobAlertModelRoot = jobAlertModelRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public SmartClassSearchRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.smartclass_search_result_layout, parent, false);
        return new SmartClassSearchRecycler.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.jobTitle.setText(jobAlertModelRoot.job_details.get(position).job_title);
        holder.companyName.setText(jobAlertModelRoot.job_details.get(position).company_name);
        Glide.with(context).asBitmap().load(jobAlertModelRoot.job_details.get(position).company_logo).into(holder.companyLogo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), SmartClassAppliedJobDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("company_name", jobAlertModelRoot.job_details.get(position).company_name);
                intent.putExtra("job_title", jobAlertModelRoot.job_details.get(position).job_title);
                intent.putExtra("qualification", jobAlertModelRoot.job_details.get(position).qualification);
                intent.putExtra("job_description", jobAlertModelRoot.job_details.get(position).job_description);
                intent.putExtra("prefered_skill", jobAlertModelRoot.job_details.get(position).prefered_skill);
                intent.putExtra("job_location", jobAlertModelRoot.job_details.get(position).job_location);
                intent.putExtra("job_salary", jobAlertModelRoot.job_details.get(position).job_salary);
                intent.putExtra("start_date", jobAlertModelRoot.job_details.get(position).start_date);
                intent.putExtra("end_date", jobAlertModelRoot.job_details.get(position).end_date);
                intent.putExtra("jobId",jobAlertModelRoot.job_details.get(position).id);
                intent.putExtra("company_logo",jobAlertModelRoot.job_details.get(position).company_logo);
                //intent.putExtra("apply_status",jobAlertModelRoot.job_details.get(position).apply_status);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobAlertModelRoot.job_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView jobTitle, companyName;
        ImageView companyLogo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.job_title);
            companyName = itemView.findViewById(R.id.company_name);
            companyLogo=itemView.findViewById(R.id.job_alert_company_logo);
        }
    }
}
