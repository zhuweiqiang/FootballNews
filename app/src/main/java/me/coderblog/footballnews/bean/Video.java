package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 集锦和视频实体类
 */
public class Video {

    public int id;
    public String label;
    public int max;
    public int min;
    public String next;
    public int page;
    public String prev;
    public List<Articles> articles;

    public static class Articles {
        public String channel;
        public int comments_total;
        public int id;
        public String published_at;
        public String scheme;
        public String share;
        public String share_title;
        public String thumb;
        public String title;
        public boolean top;
        public String url;
        public String url1;
        public String label;
    }
}
