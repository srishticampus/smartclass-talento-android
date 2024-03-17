package com.srishti.talento;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

public class GatePassHistoryAdapter extends RecyclerView.Adapter<GatePassHistoryAdapter.MyViewHolder> {

    TalentoTwoRoot root;
    Context context;


    public GatePassHistoryAdapter(TalentoTwoRoot root, Context context) {
        this.root = root;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gate_pass_history_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.startDateTv.setText(root.gatepass_details.get(position).start_date);
        holder.endDateTv.setText(root.gatepass_details.get(position).end_date);
        holder.statusTv.setText(root.gatepass_details.get(position).status);
        if (root.gatepass_details.get(position).status.equals("approved")) {
            holder.statusTv.setTextColor(Color.parseColor("#43A047"));
            holder.dwnldIconImg.setVisibility(View.VISIBLE);
            holder.dwnldIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(root.gatepass_details.get(position).gate_pass));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        } else if (root.gatepass_details.get(position).status.equals("pending")) {
            holder.statusTv.setTextColor(Color.parseColor("#26509C"));
            holder.dwnldIconImg.setVisibility(View.INVISIBLE);

        } else if (root.gatepass_details.get(position).status.equals("rejected")) {
            holder.statusTv.setTextColor(Color.parseColor("#FF0000"));
            holder.dwnldIconImg.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return root.gatepass_details.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView startDateTv;
        private TextView endDateTv;
        private TextView statusTv;
        private ImageView dwnldIconImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            startDateTv = itemView.findViewById(R.id.start_date_tv);
            endDateTv = itemView.findViewById(R.id.end_date_tv);
            statusTv = itemView.findViewById(R.id.status_tv);
            dwnldIconImg = itemView.findViewById(R.id.dwnld_icon_img);
        }
    }
}
