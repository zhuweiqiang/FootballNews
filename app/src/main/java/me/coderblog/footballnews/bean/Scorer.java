package me.coderblog.footballnews.bean;

/**
 * 射手榜实体类
 */
public class Scorer {

    public String person_id;
    public String name;
    public String team_id;
    public String team_name;
    public String count;

    public Scorer(String person_id, String name, String team_id, String team_name, String count) {
        this.person_id = person_id;
        this.name = name;
        this.team_id = team_id;
        this.team_name = team_name;
        this.count = count;
    }
}
