package me.coderblog.footballnews.bean;

import java.util.List;

/**
 * 积分榜实体类
 */
public class Rank {


    public String round_id;
    public String name;
    public String groups;
    public String rankingsCount;
    public String aggrCount;
    public String matchCount;
    public List<Rankings> rankings;

    public static class Rankings {
        public String id;
        public String rank;
        public String adjust_rank;
        public String last_rank;
        public String team_id;
        public String club_name;
        public String matches_total;
        public String matches_won;
        public String matches_draw;
        public String matches_lost;
        public String goals_pro;
        public String goals_against;
        public String points;
    }
}
