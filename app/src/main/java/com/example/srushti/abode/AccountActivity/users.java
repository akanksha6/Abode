package com.example.srushti.abode.AccountActivity;

import android.widget.Button;

import java.util.ArrayList;

public class users {
    public String name,address,phone,rent,type,fee,cases,ProImg,id;
    public ArrayList<Upload> uploads;
    public users()
    {

    }
    public users(String name, String address, String phone,String rent,String type){
        this.name=name;
        this.phone=phone;
        this.address=address;
        this.rent=rent;
        this.type=type;
    }
    public users(String name,String phone,String fee,String ProImg,String id,int a)
    {
        this.name=name;
        this.phone=phone;
        this.fee=fee;
        this.ProImg=ProImg;
        this.id=id;
    }
    public users(String name, String address,String rent){
        this.name=name;
        this.address=address;
        this.rent=rent;
    }
    public users(String name, String address){
        this.name=name;
        this.address=address;
    }
    public users(String name, String phone,int a){
        this.name=name;
        this.phone=phone;
    }



    public String getname() {
        return name;
    }

    public String getaddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getphone(){return phone;}

    public  String getrent(){return rent; }

    public void setphone(String phone){this.phone=phone;}

    public void setrent(String rent){this.rent=rent;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFee() {
        return fee;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCases() {
        return cases;
    }

    public String getProImg() {
        return ProImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Upload> getUploads() {
        return uploads;
    }

    public void setUploads(ArrayList<Upload> uploads) {
        this.uploads = uploads;
    }
}
