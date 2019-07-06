package com.ant.bean;

public class Teacher {
    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tname='" + tname + '\'' +
                '}';
    }
}
