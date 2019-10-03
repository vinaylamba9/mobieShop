package com.example.cr7.mobieshop;

import android.content.Context;

public class upload_userdata {
    Context ct;
    private String user_name,pass_word,e_mail,phone_No,reg_number,profile_pic_url,address;
    String account_date;
    String uploadid;

    public upload_userdata()
    {

    }

    public upload_userdata(String user_name, String pass_word, String e_mail, String phone_No, String reg_number,String profile_pic_url,String account_date,String address,String uploadid) {
        this.user_name = user_name;
        this.pass_word = pass_word;
        this.e_mail = e_mail;
        this.phone_No = phone_No;
        this.reg_number = reg_number;
        this.profile_pic_url = profile_pic_url;
        this.account_date = account_date;
        this.address = address;
        this.uploadid = uploadid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }
    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getAccount_date() {
        return account_date;
    }

    public void setAccount_date(String account_date) {
        this.account_date = account_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUploadid() {
        return uploadid;
    }

    public void setUploadid(String uploadid) {
        this.uploadid = uploadid;
    }
}
