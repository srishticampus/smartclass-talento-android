package com.srishti.talento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.srishti.talento.JobAlertModel.JobAlertModelRoot;

public class SmartClassHomeViewPagerAdapter extends PagerAdapter {

    Context context;
    JobAlertModelRoot jobAlertModelRoot;
    LayoutInflater layoutInflater;
    SmartClassJobAlertFragment smartClassJobAlertFragment;

    public SmartClassHomeViewPagerAdapter(Context context, JobAlertModelRoot jobAlertModelRoot) {
        this.context = context;
        this.jobAlertModelRoot = jobAlertModelRoot;
        //this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.home_screen_job_alert_view_pager_adapter, container, false);


        TextView jobTitle = itemView.findViewById(R.id.view_pager_txt2);
        jobTitle.setText(jobAlertModelRoot.job_details.get(position).job_title);
        TextView companyName = itemView.findViewById(R.id.view_pager_txt3);
        companyName.setText(jobAlertModelRoot.job_details.get(position).company_name);
        TextView applicationStartDate = itemView.findViewById(R.id.view_pager_txt_4);
        applicationStartDate.setText(jobAlertModelRoot.job_details.get(position).start_date);
        TextView jobQulification = itemView.findViewById(R.id.view_pager_txt_6);
        jobQulification.setText(jobAlertModelRoot.job_details.get(position).job_description);

        TextView viewMore=itemView.findViewById(R.id.view_pager_txt1);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context.getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();

                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                SmartClassJobAlertFragment smartClassJobAlertFragment=new SmartClassJobAlertFragment();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment,smartClassJobAlertFragment).addToBackStack(null).commit();

//
//                smartClassJobAlertFragment=new SmartClassJobAlertFragment();
//
//                ((FragmentActivity) view.getContext()).getFragmentManager().beginTransaction()
//                        .replace(R.id.home_fragment,SmartClassJobAlertFragment.class)
//                        .commit();

            }
        });

        ViewPager viewPager =(ViewPager)container;
        viewPager.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
