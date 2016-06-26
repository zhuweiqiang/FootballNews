package me.coderblog.footballnews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.adapter.RankAdapter;
import me.coderblog.footballnews.bean.Rank;
import me.coderblog.footballnews.util.Utils;

/**
 * 积分榜基础Fragment
 */
public abstract class BaseRankFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    //变量声明
    private ListView listView;
    private Rank rank;
    private List<Rank.Rankings> rankingses;
    private RankAdapter rankAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = Utils.inflate(R.layout.fragment_rank);

        //实例化listview
        listView = (ListView) view.findViewById(R.id.lv_rank);

        //首次从服务器中加载数据
        loadDataFromServer();

        return view;
    }

    //首次从服务中获取数据
    public abstract void loadDataFromServer();

    //请求成功
    @Override
    public void onResponse(String s) {
        //处理字符串
        String jsonString = s.substring(1, s.length()-1);
        //Json解析
        Gson gson = new Gson();
        rank = gson.fromJson(jsonString, Rank.class);
        //运行在主线程中
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //数据集
                rankingses = rank.rankings;
                //listview适配器
                rankAdapter = new RankAdapter(rankingses);
                listView.setAdapter(rankAdapter);
            }
        });
    }

    //请求失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

}
