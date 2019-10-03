package com.example.cr7.mobieshop;

public class my_add_data {

    String image_url;
    String product_name;
    String mPrice;
    String post_time;
    String mail;
    String uploadid;
    String category;
    String upload_item_id;

    public my_add_data() {

    }

    public my_add_data(String image_url, String product_name, String mPrice, String post_time,String mail,String uploadid,String category,String upload_item_id) {
        this.image_url = image_url;
        this.product_name = product_name;
        this.mPrice = mPrice;
        this.post_time = post_time;
        this.mail = mail;
        this.uploadid = uploadid;
        this.category = category;
        this.upload_item_id = upload_item_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUploadid() {
        return uploadid;
    }

    public void setUploadid(String uploadid) {
        this.uploadid = uploadid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpload_item_id() {
        return upload_item_id;
    }

    public void setUpload_item_id(String upload_item_id) {
        this.upload_item_id = upload_item_id;
    }
}
