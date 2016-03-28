package com.naturesouq.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naturesouq.R;

/**
 * Created by shahid on 10/19/2015.
 */
public class ViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
    Context context;
    int[] backgroundImage;
    int[] anim_circle;
    LayoutInflater inflater;
    int length;
    ImageView image;
    ImageView imagebg;

    public ViewPagerAdapter(Context context,int[] backgroundImage,ImageView imagebg, int[] anim_circle,ImageView image) {

        this.context = context;
        this.backgroundImage = backgroundImage;
        this.imagebg = imagebg;
        this.anim_circle = anim_circle;
        this.image = image ;

    }

    @Override
    public int getCount() {

        length = anim_circle.length;
        return length;

    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return view == ((RelativeLayout) o);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        ImageView imageView;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View itemView = inflater.inflate(R.layout.home_slider_item, container, false);
       // RelativeLayout relativeLayout= (RelativeLayout)itemView.findViewById(R.id.rlWelcome);
        //relativeLayout.setBackgroundResource(backgroundImage[position]);
//

        // Add viewpager_item.xml to ViewPager
        //((ViewPager) container).addView(itemView);

        return container;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        //((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

        image.setImageResource(anim_circle[position]);
        imagebg.setImageResource(backgroundImage[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




}
