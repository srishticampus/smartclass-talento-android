package com.srishti.talento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.Notification_Model.NotificationModelRoot;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.MyViewHolder> {

    NotificationModelRoot notificationModelRoot;
    Context context;

    public NotificationRecyclerAdapter(NotificationModelRoot notificationModelRoot, Context context) {
        this.notificationModelRoot = notificationModelRoot;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.smartclass_notification_recycler_layout, parent, false);
        return new NotificationRecyclerAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerAdapter.MyViewHolder holder, int position) {

        holder.title.setText(notificationModelRoot.notification_details.get(position).title);
        holder.description.setText(notificationModelRoot.notification_details.get(position).description);
        holder.date.setText(notificationModelRoot.notification_details.get(position).date);
        holder.iconTxt.setText(notificationModelRoot.notification_details.get(position).title.substring(0,2).toUpperCase());

        if (notificationModelRoot.notification_details.get(position).type.equals("a")) {
            holder.roundlayout.setBackgroundResource(R.drawable.notification_circle_bg);
        } else if (notificationModelRoot.notification_details.get(position).type.equals("b")) {
            holder.roundlayout.setBackgroundResource(R.drawable.notification_blue_bg);
        } else if (notificationModelRoot.notification_details.get(position).type.equals("c")) {
            holder.roundlayout.setBackgroundResource(R.drawable.notification_circle_yellow_bg);
        }


    }

    @Override
    public int getItemCount() {
        return notificationModelRoot.notification_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date,iconTxt;
        RelativeLayout roundlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notification_title);
            description = itemView.findViewById(R.id.notification_description);
            date = itemView.findViewById(R.id.notification_date);
            roundlayout = itemView.findViewById(R.id.notification_list_rl);
            iconTxt=itemView.findViewById(R.id.notification_icon_txt);
        }
    }
}
