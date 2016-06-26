package me.coderblog.footballnews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.activity.WebViewActivity;
import me.coderblog.footballnews.adapter.VideoAdapter;
import me.coderblog.footballnews.bean.Video;
import me.coderblog.footballnews.util.Utils;

/**
 * 集锦和视频的基础Fragment
 */
public abstract class VideoBaseFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    //变量声明
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Video.Articles> articles;
    private VideoAdapter videoAdapter;
    private Video video;
    private boolean isLoading;
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = Utils.inflate(R.layout.fragment_only_listview);

        //实例化listview
        listView = (ListView) view.findViewById(R.id.lv_collection);
        //添加加载更多布局
        footerView = Utils.inflate(R.layout.list_item_load_more);
        footerView.setClickable(false);
        listView.addFooterView(footerView, null, false);
        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Utils.getContext(), WebViewActivity.class);
                intent.putExtra("url", articles.get(position).url);
                startActivity(intent);
            }
        });
        //滑动事件
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //加载更多的触发条件
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listView.getLastVisiblePosition() == articles.size()
                        && !isLoading) {
                    loadMoreDataFromServer(video);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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

        return view;
    }

    //首次从服务中获取数据
    public abstract void loadDataFromServer();

    //请求成功
    @Override
    public void onResponse(String s) {
        //Json解析
        Gson gson = new Gson();
        video = gson.fromJson(s, Video.class);
        //运行在主线程中
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //处理后的数据集
                articles = video.articles;
                //listview适配器
                videoAdapter = new VideoAdapter(articles);
                listView.setAdapter(videoAdapter);
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
    public void loadMoreDataFromServer(final Video anotherVideo) {
        isLoading = true;
        footerView.setVisibility(View.VISIBLE);
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(anotherVideo.next,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Json解析
                        Gson gson = new Gson();
                        video = gson.fromJson(s, Video.class);
                        articles.addAll(video.articles);
                        videoAdapter.notifyDataSetChanged();
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
}
