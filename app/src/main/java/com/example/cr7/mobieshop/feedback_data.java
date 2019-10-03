package com.example.cr7.mobieshop;

public class feedback_data {

    String user_name;
    String regno;
    String feed_back;
    String time_post;

    public feedback_data()
    {

    }

    public feedback_data(String user_name, String regno, String feed_back,String time_post) {
        this.user_name = user_name;
        this.regno = regno;
        this.feed_back = feed_back;
        this.time_post = time_post;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getFeed_back() {
        return feed_back;
    }

    public void setFeed_back(String feed_back) {
        this.feed_back = feed_back;
    }

    public String getTime_post() {
        return time_post;
    }

    public void setTime_post(String time_post) {
        this.time_post = time_post;
    }
}
