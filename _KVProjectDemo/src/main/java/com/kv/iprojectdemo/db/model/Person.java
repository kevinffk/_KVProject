package com.kv.iprojectdemo.db.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Person implements Serializable {

    private static final long serialVersionUID = 5710012071925213902L;

    
    
    public Person(String jobNo, String name, int age, String job, String sex, boolean isMarried) {
        this.jobNo = jobNo;
        this.name = name;
        this.age = age;
        this.job = job;
        this.sex = sex;
        this.isMarried = isMarried;
    }
    
    public Person() {
        
    }


    @DatabaseField(id=true)
    private String jobNo;
    
    @DatabaseField
    private String name;
    
    @DatabaseField
    private int age;
    
    @DatabaseField
    private String job;
    
    @DatabaseField
    private String sex;
    
    @DatabaseField(canBeNull=false, defaultValue="false")
    private boolean isMarried;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean isMarried) {
        this.isMarried = isMarried;
    }
    
    
}
