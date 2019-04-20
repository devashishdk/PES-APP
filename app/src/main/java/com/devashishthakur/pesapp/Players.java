package com.devashishthakur.pesapp;

import com.google.firebase.database.Exclude;

public class Players {

    String playerpesname;
    String overallpesrating;
    String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    String pageno;

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    String pushid;
    public String getPlayerpesname() {
        return playerpesname;
    }

    public void setPlayerpesname(String playerpesname) {
        this.playerpesname = playerpesname;
    }

    public String getOverallpesrating() {
        return overallpesrating;
    }

    public void setOverallpesrating(String overallpesrating) {
        this.overallpesrating = overallpesrating;
    }

    public Players(String playerpesname, String overallpesrating,String pushid,String pageno,String position) {
        this.playerpesname = playerpesname;
        this.overallpesrating = overallpesrating;
        this.pushid = pushid;
        this.pageno = pageno;
        this.position = position;
    }

    public  Players()
    {

    }

}
