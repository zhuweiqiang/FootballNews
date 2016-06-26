package me.coderblog.footballnews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.activity.WebViewActivity;
import me.coderblog.footballnews.adapter.TopAdapter;
import me.coderblog.footballnews.adapter.TopViewPagerAdapter;
import me.coderblog.footballnews.bean.Top;
import me.coderblog.footballnews.global.Http;
import me.coderblog.footballnews.util.Utils;

/**
 * 头条
 */
public class TopFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    //变量声明
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Top.Articles> articles;
    private List<Top.Recommend> recommends;
    private TopAdapter topAdapter;
    private Top top;
    private TopViewPagerAdapter topViewPagerAdapter;
    private boolean isLoading;
    private boolean isRunning;//控制定时器
    private ViewPager viewPager;
    private View footerView;
    private TextView title;
    private LinearLayout linearLayout;

    //实现延时跳转
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //跳转到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            //循环发送消息
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = Utils.inflate(R.layout.fragment_only_listview);
        isRunning = true;
        //实例化控件
        listView = (ListView) view.findViewById(R.id.lv_collection);

        //添加头布局
        View headerView = Utils.inflate(R.layout.list_item_headerview);
        listView.addHeaderView(headerView, null, false);
        viewPager = (ViewPager) headerView.findViewById(R.id.view_pager);
        title = (TextView) headerView.findViewById(R.id.title);
        linearLayout = (LinearLayout) headerView.findViewById(R.id.dot_container);

        //添加加载更多布局
        footerView = Utils.inflate(R.layout.list_item_load_more);
        footerView.setClickable(false);
        listView.addFooterView(footerView, null, false);

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Utils.getContext(), WebViewActivity.class);
                intent.putExtra("url", articles.get(position - 1).url);
                startActivity(intent);
            }
        });

        //listview滑动事件
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //加载更多的触发条件
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listView.getLastVisiblePosition() == articles.size() + 1
                        && !isLoading) {
                    loadMoreDataFromServer(top);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //viewpager监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //标题滚动
                int newPosition = position % recommends.size();
                title.setText(recommends.get(newPosition).title);

                //原点指示器滚动
                //首先linearLayout中子view全部不选中
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    linearLayout.getChildAt(i).setEnabled(false);
                }
                //指定位置选中
                linearLayout.getChildAt(newPosition).setEnabled(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataFromServer();
            }
        });

        //首次从服务器中加载数据
        loadDataFromServer();

        //延时跳转
        handler.sendEmptyMessageDelayed(0, 3000);

        return view;
    }

    //首次从服务中获取数据
    public void loadDataFromServer() {
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(Http.URL_TOP, this, this);
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    //请求成功
    @Override
    public void onResponse(String s) {
        //Json解析
        Gson gson = new Gson();
        top = gson.fromJson(s, Top.class);
        //运行在主线程中
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //处理后的数据集
                articles = handleList(top.articles);
                recommends = handle(top.recommend);

                //viewpager适配器
                topViewPagerAdapter = new TopViewPagerAdapter(recommends);
                viewPager.setAdapter(topViewPagerAdapter);

                //初始化时设定viewpager第一页标题
                title.setText(recommends.get(0).title);

                //listview适配器
                topAdapter = new TopAdapter(articles);
                listView.setAdapter(topAdapter);

                //初始化圆点指示器
                initDot(recommends);
                linearLayout.getChildAt(0).setEnabled(true);//默认选中第一个

                //数据加载完毕刷新停止
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //请求失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    //从服务器中加载更多
    public void loadMoreDataFromServer(final Top anotherTop) {
        isLoading = true;
        footerView.setVisibility(View.VISIBLE);
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(anotherTop.next,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Json解析
                        Gson gson = new Gson();
                        top = gson.fromJson(s, Top.class);
                        articles.addAll(handleList(top.articles));
                        topAdapter.notifyDataSetChanged();
                        isLoading = false;
                        footerView.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    //控制定时器
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRunning = false;
    }

    //去除ListView中圈子、直播、广告和装备
    private List<Top.Articles> handleList(List<Top.Articles> articles) {
        Iterator<Top.Articles> iterator = articles.iterator();

        while (iterator.hasNext()) {
            Top.Articles article = iterator.next();

            if (!article.url.contains("http")) {
                iterator.remove();
            } else if (article.label != null && article.label.equals("推广")) {
                iterator.remove();
            } else if (article.label != null && article.label.equals("装备")) {
                iterator.remove();
            }
        }

        return articles;
    }

    //去除首页轮播图中的圈子、直播、广告 和 装备
    private List<Top.Recommend> handle(List<Top.Recommend> recommends) {
        Iterator<Top.Recommend> iterator = recommends.iterator();

        while (iterator.hasNext()) {
            Top.Recommend recommend = iterator.next();

            if (!recommend.url.contains("http")) {
                iterator.remove();
            } else if (recommend.label != null && recommend.label.equals("推广")) {
                iterator.remove();
            } else if (recommend.label != null && recommend.label.equals("装备")) {
                iterator.remove();
            }
        }

        return recommends;
    }

    //初始化指示器
    private void initDot(List<Top.Recommend> recommends) {
        //如果有子view，首先移除所有，避免下拉刷新时重复添加原点
        if (linearLayout.getChildCount() != 0) {
            linearLayout.removeAllViews();
        }
        //viewpager中图片的数量
        int count = recommends.size();

        for (int i = 0; i < count; i++) {
            //创建view对象
            View view = new View(Utils.getContext());
            //设置view的大小和位置
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(13, 13);
            if (i != 0) { //第一个点不需要间隔
                layoutParams.leftMargin = 10;
            }
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.drawable.viewpager_dot_selector);
            view.setEnabled(false); //开始全部为不选中
            linearLayout.addView(view);
        }
    }

}
