package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.League;
import me.coderblog.footballnews.util.Utils;

/**
 * 中超 英超 西甲 德甲 意甲 listview的适配器
 */
public class LeagueAdapter extends BaseAdapter {

    private static final int NORMAL = 0;
    private static final int PICTURES = 1;

    private List<League.Articles> articles;

    public LeagueAdapter(List<League.Articles> articles) {
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        League.Articles article = articles.get(position);

        if (article.album != null) {
            return PICTURES;
        } else {
            return NORMAL;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        League.Articles article = articles.get(position);
        int type = getItemViewType(position);

        ViewHolder viewHolder;
        PicViewHolder picViewHolder;

        if (type == PICTURES) {

            if (convertView == null) {
                convertView = Utils.inflate(R.layout.list_item_league_pictures);

                picViewHolder = new PicViewHolder();
                picViewHolder.title = (TextView) convertView.findViewById(R.id.title);
                picViewHolder.icon1 = (ImageView) convertView.findViewById(R.id.icon1);
                picViewHolder.icon2 = (ImageView) convertView.findViewById(R.id.icon2);
                picViewHolder.icon3 = (ImageView) convertView.findViewById(R.id.icon3);

                convertView.setTag(picViewHolder);
            } else {
                picViewHolder = (PicViewHolder) convertView.getTag();
            }

            picViewHolder.title.setText(article.title);
            Glide.with(Utils.getContext()).load(article.album.pics.get(0)).centerCrop().into(picViewHolder.icon1);
            Glide.with(Utils.getContext()).load(article.album.pics.get(1)).centerCrop().into(picViewHolder.icon2);
            Glide.with(Utils.getContext()).load(article.album.pics.get(2)).centerCrop().into(picViewHolder.icon3);

        } else {

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

            Glide.with(Utils.getContext()).load(article.thumb).centerCrop().into(viewHolder.icon);
            viewHolder.title.setText(article.title);
            viewHolder.desc.setText(article.description);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
    }

    class PicViewHolder {
        TextView title;
        ImageView icon1, icon2, icon3;
    }
}
