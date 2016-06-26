package me.coderblog.footballnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.fragment.RankFragmentFactory;
import me.coderblog.footballnews.util.Utils;

/**
 * 积分榜Viewpager适配器
 */
public class RankViewPager extends FragmentPagerAdapter {

    //tab标签的标题
    private String[] tabTitle;

    public RankViewPager(FragmentManager fm) {
        super(fm);
        tabTitle = Utils.getStringArray(R.array.tab_league_title);
    }

    @Override
    public Fragment getItem(int position) {
        return RankFragmentFactory.createFragment(position);
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
