package com.example.cr7.mobieshop;
import android.content.Context;
import android.widget.Toast;

public class upload_data {
    private String mName;
    private String mImageUrl;
    private int mPrice;
    private String mDesc;
    private String mBrandName;
    private int mOriginalPrice;
    private String mDuration;
    private String mCategory;
    private String user_id;
    private String post_time;
    private String uploadid;
    private Context c;

    public upload_data()
    {
        //needed constructor
    }

    public upload_data(String mName, String mImageUrl, int mPrice, String mDesc,String mBrandName,int mOriginalPrice,String mDuration,String mCategory,String user_id,String post_time,String uploadid) {
        if(mName.trim().equals(""))
        {
            mName = "No Name";
        }
        if(mPrice == 0)
        {
            mPrice = 100;
        }
        if(mOriginalPrice ==0)
        {
            mOriginalPrice = 100;
        }
        if(mBrandName.trim().equals(""))
        {
            mBrandName = "Brand Name not Mentioned!";
        }
        if(mDuration.trim().equals(""))
        {
            mDuration = "Used time of item not Mentioned!";
        }
        if(mCategory.trim().equals("Choose Category of your Item."))
        {
            mCategory = "Other";
        }
        if(mDesc.trim().equals(""))
        {
            mDesc = "No Description Mentioned!";
        }
        this.mName = mName;
        this.mImageUrl = mImageUrl;
        this.mPrice = mPrice;
        this.mDesc = mDesc;
        this.mBrandName = mBrandName;
        this.mOriginalPrice = mOriginalPrice;
        this.mDuration = mDuration;
        this.mCategory = mCategory;
        this.user_id = user_id;
        this.post_time = post_time;
        this.uploadid = uploadid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmBrandName() {
        return mBrandName;
    }

    public void setmBrandName(String mBrandName) {
        this.mBrandName = mBrandName;
    }

    public int getmOriginalPrice() {
        return mOriginalPrice;
    }

    public void setmOriginalPrice(int mOriginalPrice) {
        this.mOriginalPrice = mOriginalPrice;
    }

    public String getmDuration() {
        return mDuration;
    }

    public void setmDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getUploadid() {
        return uploadid;
    }

    public void setUploadid(String uploadid) {
        this.uploadid = uploadid;
    }
}
