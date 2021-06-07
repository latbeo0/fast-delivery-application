package com.uniapp.fastdeliveryappilcation.model;


public class Option {
    int imgid;
    String name;
    String descp;
    public Option()
    {
    }
    public Option(int imgid, String name, String descp) {
        this.imgid = imgid;
        this.name = name;
        this.descp = descp;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
