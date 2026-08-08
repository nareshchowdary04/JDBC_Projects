package com.complaint.model;

public class Officer {

    private int officerId;
    private String name;
    private String department;

    public Officer() {
    }

    public Officer(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}