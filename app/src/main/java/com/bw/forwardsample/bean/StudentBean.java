package com.bw.forwardsample.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

//标识 该类映射一张表
@Entity()
public class StudentBean {

    //一个自增的主键
    @Id(autoincrement = true)
    private Long id;

    private String name;

    // ageXxxx 映射表中的列名为 age
    @Property(nameInDb = "age")
    private int ageXxxx;

    //表中不需要该字段
    @Transient
    private int yyyy;

    @Generated(hash = 980124091)
    public StudentBean(Long id, String name, int ageXxxx) {
        this.id = id;
        this.name = name;
        this.ageXxxx = ageXxxx;
    }

    @Generated(hash = 2097171990)
    public StudentBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeXxxx() {
        return this.ageXxxx;
    }

    public void setAgeXxxx(int ageXxxx) {
        this.ageXxxx = ageXxxx;
    }



}
