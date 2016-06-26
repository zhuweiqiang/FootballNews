package me.coderblog.footballnews.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.coderblog.footballnews.R;
import me.coderblog.footballnews.bean.Subject;
import me.coderblog.footballnews.util.Utils;

/**
 * 专题适配器
 */
public class SubjectAdapter extends BaseAdapter {

    private List<Subject.Articles> articles;

    public SubjectAdapter(List<Subject.Articles> articles) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Subject.Articles article = articles.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = Utils.inflate(R.layout.list_item_subject);

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

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView title, desc;
    }
}
