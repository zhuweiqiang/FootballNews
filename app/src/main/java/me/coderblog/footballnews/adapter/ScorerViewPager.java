package me.coderblog.footballnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.fragment.ScorerFragmentFactory;
import me.coderblog.footballnews.util.Utils;

/**
 * 射手榜viewpager适配器
 */
public class ScorerViewPager extends FragmentPagerAdapter {

    //tab标签的标题
    private String[] tabTitle;

    public ScorerViewPager(FragmentManager fm) {
        super(fm);
        tabTitle = Utils.getStringArray(R.array.tab_league_title);
    }

    @Override
    public Fragment getItem(int position) {
        return ScorerFragmentFactory.createFragment(position);
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
