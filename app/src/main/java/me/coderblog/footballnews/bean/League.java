package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 转会 中超 英超 西甲 德甲 意甲 五洲实体类
 */
public class League {


    public int id;
    public String label;
    public String prev;
    public String next;
    public int max;
    public int min;
    public int page;
    public List<Articles> articles;

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
        public Cover cover;
        public Album album;
        public Topic topic;
        public String label;
        public String label_color;
        public List<String> extend;

        public static class Cover {
            public String pic;
        }

        public static class Album {
            public int total;
            public List<String> pics;
        }

        public static class Topic {
            public String id;
            public String thumb;
            public String content;
            public Group group;
            public Author author;

            public static class Group {
                public int id;
                public String title;
            }

            public static class Author {
                public int id;
                public String username;
            }
        }
    }


}
