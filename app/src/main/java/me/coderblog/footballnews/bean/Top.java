package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 头条实体类
 */
public class Top {

    public int id;
    public String label;
    public String prev;
    public String next;
    public int max;
    public int min;
    public int page;
    public List<Articles> articles;
    public List<Recommend> recommend;

    public static class Articles {
        public int id;
        public String title;
        public String share_title;
        public String description;
        public int comments_total;
        public String share;
        public String thumb;
        public String published_at;
        public boolean top;
        public String url;
        public String url1;
        public String scheme;
        public String channel;
        public String label;
        public String label_color;
        public Album album;

        public static class Album {
            public int total;
            public List<String> pics;
        }
    }

    public static class Recommend {
        public int id;
        public String title;
        public String share_title;
        public String description;
        public int comments_total;
        public String share;
        public String thumb;
        public String published_at;
        public boolean top;
        public String url;
        public String label;
        public String url1;
        public String scheme;
        public String channel;
    }
}
