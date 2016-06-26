package me.coderblog.footballnews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.adapter.ScorerAdapter;
import me.coderblog.footballnews.bean.Scorer;
import me.coderblog.footballnews.util.Utils;

/**
 * 积分榜基础Fragment
 */
public abstract class BaseScorerFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    //变量声明
    private ListView listView;
    private List<Scorer> scorers = new ArrayList<Scorer>();
    private ScorerAdapter scorerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = Utils.inflate(R.layout.fragment_scorer);

        //实例化listview
        listView = (ListView) view.findViewById(R.id.lv_scorer);

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
        parseJson(s);

        //运行在主线程中
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scorerAdapter = new ScorerAdapter(scorers);
                listView.setAdapter(scorerAdapter);
            }
        });
    }

    private void parseJson(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String person_id = jsonObject.getString("person_id");
                String name = jsonObject.getString("name");
                String team_id = jsonObject.getString("team_id");
                String team_name = jsonObject.getString("team_name");
                String count = jsonObject.getString("count");
                Scorer scorer = new Scorer(person_id, name, team_id, team_name, count);
                scorers.add(scorer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //请求失败
    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
