package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

public class ExamDetailsRecyclerAdapter extends RecyclerView.Adapter<ExamDetailsRecyclerAdapter.MyViewHolder> {
    TalentoTwoRoot examDetailsRoot;
    Context context;
    String techId;

    String courseImageUrl;

    public ExamDetailsRecyclerAdapter(TalentoTwoRoot examDetailsRoot, Context context, String techId, String courseImageUrl) {
        this.examDetailsRoot = examDetailsRoot;
        this.context = context;
        this.techId = techId;
        this.courseImageUrl = courseImageUrl;
    }

    @NonNull
    @Override
    public ExamDetailsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_details_recycler_layout, parent, false);
        return new ExamDetailsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamDetailsRecyclerAdapter.MyViewHolder holder, int position) {

        if (examDetailsRoot.examDetails.get(position).lock_status.equals("1")) {
            holder.lockLayout.setVisibility(View.GONE);
        } else {
            holder.lockLayout.setVisibility(View.VISIBLE);
        }

        holder.testName.setText(examDetailsRoot.examDetails.get(position).test_name);
        holder.duration.setText(examDetailsRoot.examDetails.get(position).duration);

        try {
            if (examDetailsRoot.examDetails.get(position).selection_test) {
                holder.noQn.setText("75");
            } else {
                holder.noQn.setText(examDetailsRoot.examDetails.get(position).no_of_ques);
            }
        } catch (Exception e) {

        }

        try {
            Glide.with(context).asBitmap().
                    load(examDetailsRoot.examDetails
                            .get(position).exam_icon).
                    into(holder.imageView);
        } catch (Exception e) {

        }
        // holder.noOfExams.setText(String.valueOf(position + 1));


        if (examDetailsRoot.examDetails.get(position).view_status == 1) {
            // holder.cardView.setCardBackgroundColor(Color.parseColor("#F9E79F"));
            holder.score.setText("Your Previous Score is " + examDetailsRoot.examDetails.get(position).score);
        } else {

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("exam_id", examDetailsRoot.examDetails.get(position).id);
                editor.apply();

                if (examDetailsRoot.examDetails.get(position).selection_test) {
                    Intent intent = new Intent(context, TalentoTwoExamCodeVerificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("que_no", examDetailsRoot.examDetails.get(position).no_of_ques);
                    intent.putExtra("duration", examDetailsRoot.examDetails.get(position).duration);
                    intent.putExtra("question_set_id", examDetailsRoot.examDetails.get(position).id);
                    intent.putExtra("techId", techId);
                    context.startActivity(intent);
                } else {
                    if (examDetailsRoot.examDetails.get(position).lock_status.equals("1")) {
                        Intent intent = new Intent(context, ExamActivity.class);
                        intent.putExtra("que_no", examDetailsRoot.examDetails.get(position).no_of_ques);
                        intent.putExtra("duration", examDetailsRoot.examDetails.get(position).duration);
                        intent.putExtra("question_set_id", examDetailsRoot.examDetails.get(position).id);
                        intent.putExtra("techId", techId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        holder.lockLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return examDetailsRoot.examDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView testName, duration, noQn, score, noOfExams;
        CardView cardView;
        RelativeLayout lockLayout;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            testName = itemView.findViewById(R.id.exam_details_testName_tv);
            duration = itemView.findViewById(R.id.exam_details_duration_tv);
            noQn = itemView.findViewById(R.id.exam_details_no_qn);
            score = itemView.findViewById(R.id.last_score_tv);
            // cardView = itemView.findViewById(R.id.exam_set_card);
            lockLayout = itemView.findViewById(R.id.exam_detail_lock_layout);
            imageView = itemView.findViewById(R.id.course_iv_new);
        }
    }
}
