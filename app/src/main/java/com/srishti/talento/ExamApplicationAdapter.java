package com.srishti.talento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

public class ExamApplicationAdapter extends RecyclerView.Adapter<ExamApplicationAdapter.MyViewHolder> {

    Context context;
    TalentoTwoRoot root;


    public ExamApplicationAdapter(Context context, TalentoTwoRoot root) {
        this.context = context;
        this.root = root;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_history_recycler_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        Glide.with(context).load(root.examHistory.get(position).technology_icon).into(holder.examLogo);
//        holder.examNameTv.setText(root.examHistory.get(position).technology_name);
//        holder.examDate.setText(root.examHistory.get(position).date);
//        holder.examStartTimeTv.setText(root.examHistory.get(position).time_start);
//        holder.examEndTimeTv.setText(root.examHistory.get(position).time_end);
//        holder.examCorrectAnswerTv.setText(root.examHistory.get(position).score);
//        // holder.examWrongAnswerTv.setText(root.examHistory.get(position).);
//        holder.examUnattendedAnswerTv.setText(root.examHistory.get(position).total_not_ate);
//        try {
//            int wrongAns = Integer.parseInt(root.examHistory.get(position).total_q) - Integer.parseInt(root.examHistory.get(position).score) - Integer.parseInt(root.examHistory.get(position).total_not_ate);
//            String wrongAnswer = String.valueOf(wrongAns);
//            holder.examWrongAnswerTv.setText(wrongAnswer);
//        } catch (Exception e) {
//
//        }
//        holder.marksObtainedTv.setText(root.examHistory.get(position).score);
//        holder.totalMarksTv.setText(root.examHistory.get(position).total_q);
        holder.examDateTv.setText(root.examHistory.get(position).date);
        holder.examTimeTv.setText(root.examHistory.get(position).time_end);
        holder.technologyNameTv.setText(root.examHistory.get(position).technology_name);
        holder.examNameTv.setText(root.examHistory.get(position).exam_title);
        holder.correctAnsCountTv.setText(root.examHistory.get(position).score);
        holder.totalQueTv.setText(root.examHistory.get(position).total_q);
        holder.correctAnsTv.setText(root.examHistory.get(position).score);
        holder.wrongAnsCountTv.setText(root.examHistory.get(position).total_wrong);
        holder.queNotAnsCountTv.setText(root.examHistory.get(position).total_not_ate);

    }

    @Override
    public int getItemCount() {
        return root.examHistory.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView4;
        private ImageView queNotAnsIconIv;
        private TextView queNotAnsCountTv;
        private CardView cardView3;
        private ImageView wrongIconIv;
        private TextView wrongAnsCountTv;
        private TextView technologyNameTv;
        private CardView cardView;
        private TextView examDateTv;
        private TextView examTimeTv;
        private TextView examNameTv;
        private CardView cardView2;
        private ImageView correctAnsIv;
        private TextView correctAnsCountTv;
        private TextView correctAnsTv;
        private TextView textView10;
        private TextView totalQueTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            cardView4 = itemView.findViewById(R.id.cardView4);
            queNotAnsIconIv = itemView.findViewById(R.id.que_not_ans_icon_iv);
            queNotAnsCountTv = itemView.findViewById(R.id.que_not_ans_count_tv);
            cardView3 = itemView.findViewById(R.id.cardView3);
            wrongIconIv = itemView.findViewById(R.id.wrong_icon_iv);
            wrongAnsCountTv = itemView.findViewById(R.id.wrong_ans_count_tv);
            technologyNameTv = itemView.findViewById(R.id.technology_name_tv);
            cardView = itemView.findViewById(R.id.cardView);
            examDateTv = itemView.findViewById(R.id.exam_date_tv);
            examTimeTv = itemView.findViewById(R.id.exam_time_tv);
            examNameTv = itemView.findViewById(R.id.exam_name_tv);
            cardView2 = itemView.findViewById(R.id.cardView2);
            correctAnsIv = itemView.findViewById(R.id.correct_ans_iv);
            correctAnsCountTv = itemView.findViewById(R.id.correct_ans_count_tv);
            correctAnsTv = itemView.findViewById(R.id.correct_ans_tv);
            textView10 = itemView.findViewById(R.id.textView10);
            totalQueTv = itemView.findViewById(R.id.total_que_tv);
        }

    }
}
