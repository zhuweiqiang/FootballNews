package me.coderblog.footballnews.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.adapter.AssistViewPager;
import me.coderblog.footballnews.util.Utils;

public class AssistActivity extends AppCompatActivity {

    //变量声明
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();      //初始化view
        initToolbar();   //初始化Toolbar
        initViewPager(); //初始化ViewPager
        initTabLayout(); //初始化TabLayout

    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.rank);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.assists);
        toolbar.setTitleTextColor(Utils.getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPager.setAdapter(new AssistViewPager(getSupportFragmentManager()));
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 监听Toolbar的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: //侧边栏
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
