package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 专题实体类
 */
public class Subject {

    public int id;
    public String label;
    public Object prev;
    public Object next;
    public List<Articles> articles;

    public static class Articles {
        public int id;
        public String title;
        public String description;
        public String thumb;
        public String published_at;
        public String channel;
        public String api;
    }
}
