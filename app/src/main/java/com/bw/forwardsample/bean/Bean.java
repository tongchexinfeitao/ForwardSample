package com.bw.forwardsample.bean;

import org.greenrobot.greendao.annotation.Entity;


public class Bean {
    private String name;
    private int age;

    public Bean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
