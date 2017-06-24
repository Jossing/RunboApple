package com.jossing.runboapple.ordermanage.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jossing.runboapple.ordermanage.view.LeftFragment;
import com.jossing.runboapple.ordermanage.view.RightFragment;

/**
 * @author Jossing , Create on 2017/4/16
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{ "我购买的", "我卖出的" };
    private Context context;

    private Fragment left;
    private Fragment right;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (left == null) {
                left = LeftFragment.newInstance(position + 1);
            }
            return left;
        } else {
            if (right == null) {
                right = RightFragment.newInstance(position + 1);
            }
            return right;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
