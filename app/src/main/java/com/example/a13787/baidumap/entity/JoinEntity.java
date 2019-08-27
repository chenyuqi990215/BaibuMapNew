package com.example.a13787.baidumap.entity;

import java.io.Serializable;

/**
 * Created by 13787 on 2019/4/5.
 */

public class JoinEntity implements Serializable
{
    private String name;
    private String school;
    private String department;
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getDepartment(){return department;}
    public void setDepartment(String department){this.department = department;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
