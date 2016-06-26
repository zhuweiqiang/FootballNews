package me.coderblog.footballnews.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.adapter.MainViewPager;
import me.coderblog.footballnews.util.NetworkState;
import me.coderblog.footballnews.util.Utils;

public class MainActivity extends AppCompatActivity {

    //变量声明
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();      //初始化view
        initToolbar();   //初始化Toolbar
        initViewPager(); //初始化ViewPager
        initTabLayout(); //初始化TabLayout
        navigationViewListener();

        //检查网络状态
        if (!NetworkState.networkConnected(this)) {
            Snackbar.make(drawerLayout,"当前没有网络连接",Snackbar.LENGTH_INDEFINITE)
                    .setAction("打开网络", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //打开系统设置应用
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    })
                    .show();
        }
    }


    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Utils.getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_navigation_white_24dp);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        viewPager.setAdapter(new MainViewPager(getSupportFragmentManager()));
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * navigationview监听器
     */
    private void navigationViewListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_rank:
                        startActivity(new Intent(MainActivity.this,RankActivity.class));
                        break;
                    case R.id.nav_scorer:
                        startActivity(new Intent(MainActivity.this,ScorerActivity.class));
                        break;
                    case R.id.nav_assists:
                        startActivity(new Intent(MainActivity.this,AssistActivity.class));
                        break;
                    case R.id.nav_about:
                        createAboutDialog();
                        break;
                }

                //关闭侧边栏
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }

                return false;
            }
        });
    }

    /**
     * 创建关于对话框
     */
    private void createAboutDialog() {
        View view = Utils.inflate(R.layout.about_dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setView(view, 50, 50, 50, 50);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 监听Toolbar的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: //侧边栏
                drawerLayout.openDrawer(Gravity.LEFT); //打开侧边栏
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

