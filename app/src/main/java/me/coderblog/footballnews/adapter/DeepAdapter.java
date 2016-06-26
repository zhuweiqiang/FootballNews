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
 * 深度listView适配器
 */
public class DeepAdapter extends BaseAdapter {

    private static final int NORMAL = 0;
    private static final int BIG_PICTURE = 1;

    private List<League.Articles> articles;

    public DeepAdapter(List<League.Articles> articles) {
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

        if (article.cover != null) {
            return BIG_PICTURE;
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

        if (type == BIG_PICTURE) {

            if (convertView == null) {
                convertView = Utils.inflate(R.layout.list_item_deep_big_pic);

                picViewHolder = new PicViewHolder();
                picViewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
                picViewHolder.title = (TextView) convertView.findViewById(R.id.title);

                convertView.setTag(picViewHolder);

            } else {
                picViewHolder = (PicViewHolder) convertView.getTag();
            }

            Glide.with(Utils.getContext()).load(article.cover.pic).centerCrop().into(picViewHolder.icon);
            picViewHolder.title.setText(article.title);

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

    class PicViewHolder {
        ImageView icon;
        TextView title;
    }

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView desc;
    }

}
