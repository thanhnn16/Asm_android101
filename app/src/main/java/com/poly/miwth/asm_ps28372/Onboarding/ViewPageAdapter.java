package com.poly.miwth.asm_ps28372.Onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.poly.miwth.asm_ps28372.R;

public class ViewPageAdapter extends PagerAdapter {

    Context context;

    int[] images = {R.drawable.hrm_1, R.drawable.hrm_2, R.drawable.hrm_3, R.drawable.hrm_4};

    int[] titles = {R.string.title_1, R.string.title_2, R.string.title_3, R.string.title_4};

    int[] descriptions = {R.string.des_1, R.string.des_2, R.string.des_3, R.string.des_4};

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideImg = (ImageView) view.findViewById(R.id.titleImg);
        TextView slideTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView slideDes = (TextView) view.findViewById(R.id.txtDes);

        slideImg.setImageResource(images[position]);
        slideTitle.setText(titles[position]);
        slideDes.setText(descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
