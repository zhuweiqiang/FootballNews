package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.Rank;
import me.coderblog.footballnews.util.Utils;

/**
 * 积分榜Listview适配器
 */
public class RankAdapter extends BaseAdapter {

    private List<Rank.Rankings> rankingses;

    public RankAdapter(List<Rank.Rankings> rankingses) {
        this.rankingses = rankingses;
    }

    @Override
    public int getCount() {
        return rankingses.size();
    }

    @Override
    public Object getItem(int position) {
        return rankingses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rank.Rankings ranking = rankingses.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = Utils.inflate(R.layout.list_item_rank);

            viewHolder = new ViewHolder();
            viewHolder.rank = (TextView) convertView.findViewById(R.id.rank);
            viewHolder.club_name = (TextView) convertView.findViewById(R.id.club_name);
            viewHolder.matches_total = (TextView) convertView.findViewById(R.id.matches_total);
            viewHolder.matches_won = (TextView) convertView.findViewById(R.id.matches_won);
            viewHolder.matches_draw = (TextView) convertView.findViewById(R.id.matches_draw);
            viewHolder.matches_lost = (TextView) convertView.findViewById(R.id.matches_lost);
            viewHolder.goals_pro = (TextView) convertView.findViewById(R.id.goals_pro);
            viewHolder.goals_against = (TextView) convertView.findViewById(R.id.goals_against);
            viewHolder.points = (TextView) convertView.findViewById(R.id.points);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rank.setText(ranking.rank);
        viewHolder.club_name.setText(ranking.club_name);
        viewHolder.matches_total.setText(ranking.matches_total);
        viewHolder.matches_won.setText(ranking.matches_won);
        viewHolder.matches_draw.setText(ranking.matches_draw);
        viewHolder.matches_lost.setText(ranking.matches_lost);
        viewHolder.goals_pro.setText(ranking.goals_pro);
        viewHolder.goals_against.setText(ranking.goals_against);
        viewHolder.points.setText(ranking.points);

        return convertView;
    }

    class ViewHolder {
        TextView rank, club_name, matches_total, matches_won,
                matches_draw, matches_lost, goals_pro,
                goals_against, points;
    }
}
