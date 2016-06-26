package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 专题详情页实体类
 */
public class SubjectDetail {

    public String id;
    public int total;
    public int per_page;
    public int current_page;
    public int last_page;
    public String banner;
    public String avatar;
    public String title;
    public String description;
    public boolean is_follow;
    public List<Data> data;

    public static class Data {
        public String title;
        public String description;
        public int comments_total;
        public String litpic;
        public String aid;
    }
}
