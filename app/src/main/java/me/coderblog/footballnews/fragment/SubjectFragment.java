package me.coderblog.footballnews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import me.coderblog.footballnews.activity.SubjectDetailActivity;
import me.coderblog.footballnews.adapter.SubjectAdapter;
import me.coderblog.footballnews.bean.Subject;
import me.coderblog.footballnews.global.Http;
import me.coderblog.footballnews.util.Utils;

/**
 * 专题
 */
public class SubjectFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener{

    //变量声明
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Subject.Articles> articles;
    private SubjectAdapter subjectAdapter;
    private Subject subject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = Utils.inflate(R.layout.fragment_only_listview);

        //实例化listview
        listView = (ListView) view.findViewById(R.id.lv_collection);

        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Utils.getContext(), SubjectDetailActivity.class);
                intent.putExtra("api", articles.get(position).api);
                startActivity(intent);
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
    public void loadDataFromServer(){
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(Http.URL_SUBJECT, this, this);
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    //请求成功
    @Override
    public void onResponse(String s) {
        //Json解析
        Gson gson = new Gson();
        subject = gson.fromJson(s, Subject.class);
        //运行在主线程中
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //数据集
                articles = subject.articles;
                //listview适配器
                subjectAdapter = new SubjectAdapter(articles);
                listView.setAdapter(subjectAdapter);
                //数据加载完毕刷新停止
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //请求失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
