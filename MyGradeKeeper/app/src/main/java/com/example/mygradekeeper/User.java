package com.example.mygradekeeper;

public class User {
    String name,pwd;
    int usertype;
    static User user;
    public User(String name,String pwd,int usertype){
        this.user=this;
        this.name=name;
        this.pwd=pwd;
        this.usertype=usertype;
    }
    public static User getUser(){
        return user;
    }

    public static void logout(){
        user=null;
    }
    public int getUsertype() {
        return usertype;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
