package com.example.srushti.abode.AccountActivity;

public class Upload {
    private  String mName,mImageUrl;

    public Upload()
    {

    }
    public Upload(String Name,String ImageUrl)
    {
            this.mImageUrl = ImageUrl;
            this.mName = Name;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
