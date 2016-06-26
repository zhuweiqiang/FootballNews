package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.SubjectDetail;
import me.coderblog.footballnews.util.Utils;

/**
 * 专题详情listview适配器
 */
public class SubjectDetailAdapter extends BaseAdapter{

    private List<SubjectDetail.Data> datas;

    public SubjectDetailAdapter(List<SubjectDetail.Data> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectDetail.Data data = datas.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = Utils.inflate(R.layout.list_item_league_normal);

            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(Utils.getContext()).load(data.litpic).centerCrop().into(viewHolder.icon);
        viewHolder.title.setText(data.title);
        viewHolder.desc.setText(data.description);
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
    }

}
