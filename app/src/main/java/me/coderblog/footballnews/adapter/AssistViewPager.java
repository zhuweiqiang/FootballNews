package me.coderblog.footballnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.fragment.AssistFragmentFactory;
import me.coderblog.footballnews.util.Utils;

/**
 * 助攻榜Viewpager适配器
 */
public class AssistViewPager extends FragmentPagerAdapter {

    //tab标签的标题
    private String[] tabTitle;

    public AssistViewPager(FragmentManager fm) {
        super(fm);
        tabTitle = Utils.getStringArray(R.array.tab_league_title);
    }

    @Override
    public Fragment getItem(int position) {
        return AssistFragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
