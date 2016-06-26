package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.Scorer;
import me.coderblog.footballnews.util.Utils;

/**
 * 射手榜listview适配器
 */
public class ScorerAdapter extends BaseAdapter {

    private List<Scorer> scorers;

    public ScorerAdapter(List<Scorer> scorers) {
        this.scorers = scorers;
    }

    @Override
    public int getCount() {
        return scorers.size();
    }

    @Override
    public Object getItem(int position) {
        return scorers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Scorer scorer = scorers.get(position);
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
        viewHolder.name.setText(scorer.name);
        viewHolder.team_name.setText(scorer.team_name);
        viewHolder.count.setText(scorer.count);

        return convertView;
    }

    class ViewHolder {
        TextView rank,name, team_name, count;
    }
}
