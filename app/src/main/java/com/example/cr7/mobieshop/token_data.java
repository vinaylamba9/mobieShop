package com.example.cr7.mobieshop;

public class token_data {

    String e_mail,messaging_token,name,user_uid;

    public token_data(){

    }

    public token_data(String e_mail, String messaging_token, String name, String user_uid) {
        this.e_mail = e_mail;
        this.messaging_token = messaging_token;
        this.name = name;
        this.user_uid = user_uid;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getMessaging_token() {
        return messaging_token;
    }

    public void setMessaging_token(String messaging_token) {
        this.messaging_token = messaging_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
}
