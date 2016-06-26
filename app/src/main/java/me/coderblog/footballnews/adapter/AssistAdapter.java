package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.Assist;
import me.coderblog.footballnews.util.Utils;

/**
 * 助攻榜listview适配器
 */
public class AssistAdapter extends BaseAdapter {

    private List<Assist> assists;

    public AssistAdapter(List<Assist> assists) {
        this.assists = assists;
    }

    @Override
    public int getCount() {
        return assists.size();
    }

    @Override
    public Object getItem(int position) {
        return assists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Assist assist = assists.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = Utils.inflate(R.layout.list_item_scorer);

            viewHolder = new ViewHolder();
            viewHolder.rank = (TextView) convertView.findViewById(R.id.rank);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.team_name = (TextView) convertView.findViewById(R.id.team_name);
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rank.setText(position + 1 + "");
        viewHolder.name.setText(assist.name);
        viewHolder.team_name.setText(assist.team_name);
        viewHolder.count.setText(assist.count);

        return convertView;
    }

    class ViewHolder {
        TextView rank,name, team_name, count;
    }
}
