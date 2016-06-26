package me.coderblog.footballnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.fragment.FragmentFactory;
import me.coderblog.footballnews.util.Utils;


/**
 * 主页的ViewPager适配器
 */
public class MainViewPager extends FragmentPagerAdapter {

    //tab标签的标题
    private String[] tabTitle;

    public MainViewPager(FragmentManager fm) {
        super(fm);
        tabTitle = Utils.getStringArray(R.array.tab_title);
    }

    //返回当前页面的Fragment
    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    //Fragment的数量
    @Override
    public int getCount() {
        return tabTitle.length;
    }

    //返回Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
