package me.coderblog.footballnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.adapter.SubjectDetailAdapter;
import me.coderblog.footballnews.bean.SubjectDetail;
import me.coderblog.footballnews.util.Utils;

/**
 * 专题详情页
 */
public class SubjectDetailActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    //常量声明
    private static final String SUBJECT = "专栏";

    //变量声明
    private Toolbar toolbar;
    private ListView listView;
    private String api;
    private SubjectDetail subjectDetail;
    private List<SubjectDetail.Data> datas;
    private SubjectDetailAdapter subjectDetailAdapter;
    private boolean isLoading;
    private View footerView;
    private int index = 1;
    private ProgressBar progressBar;
    private TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.listview);

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Utils.getContext(), WebViewActivity.class);
                intent.putExtra("url", "https://api.dongqiudi.com/article/" + datas.get(position).aid + ".html");
                startActivity(intent);
            }
        });

        //滑动事件
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //加载更多的触发条件
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && listView.getLastVisiblePosition() == datas.size()
                        && !isLoading) {

                    //索引值自增
                    index++;

                    if (index <= subjectDetail.last_page) {
                        loadMoreDataFromServer(index);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        loading.setText("已经没有更多了");
                    }

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        //初始化Toolbar
        initToolbar();

        //获取数据
        Intent intent = getIntent();
        api = intent.getStringExtra("api");

        //添加加载更多布局
        footerView = Utils.inflate(R.layout.list_item_load_more);
        progressBar = (ProgressBar) footerView.findViewById(R.id.progressBar);
        loading = (TextView) footerView.findViewById(R.id.loading);
        listView.addFooterView(footerView, null, false);

        //首次从服务器中加载数据
        loadDataFromServer(index);

    }

    //初始化Toolbar
    private void initToolbar() {
        toolbar.setTitleTextColor(Utils.getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
    }

    //首次从服务中获取数据
    public void loadDataFromServer(int index) {
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(api + "?page=" + index, this, this);
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    //请求成功
    @Override
    public void onResponse(String s) {
        //Json解析
        Gson gson = new Gson();
        subjectDetail = gson.fromJson(s, SubjectDetail.class);
        //运行在主线程中
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //设置标题栏的标题
                toolbar.setTitle(subjectDetail.title);
                //处理后的数据集
                datas = subjectDetail.data;
                //listview适配器
                subjectDetailAdapter = new SubjectDetailAdapter(datas);
                listView.setAdapter(subjectDetailAdapter);
            }
        });
    }

    //请求失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    //从服务器中加载更多
    public void loadMoreDataFromServer(int index) {
        isLoading = true;
        footerView.setVisibility(View.VISIBLE);
        //创建请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Utils.getContext());
        //创建请求
        StringRequest stringRequest = new StringRequest(api + "?page=" + index,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Json解析
                        Gson gson = new Gson();
                        subjectDetail = gson.fromJson(s, SubjectDetail.class);
                        datas.addAll(subjectDetail.data);
                        subjectDetailAdapter.notifyDataSetChanged();
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

    //监听Toolbar按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
