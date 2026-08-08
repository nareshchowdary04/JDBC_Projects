package com.leave.model;

public class Employee {
	   private int employeeId;
	    private String employeeName;
	    private String department;
	    private String email;
	    private String phone;

	    public Employee() {
	    }

	    public Employee(String employeeName, String department, String email, String phone) {

	        this.employeeName = employeeName;
	        this.department = department;
	        this.email = email;
	        this.phone = phone;

	    }

	    public int getEmployeeId() {
	        return employeeId;
	    }

	    public void setEmployeeId(int employeeId) {
	        this.employeeId = employeeId;
	    }

	    public String getEmployeeName() {
	        return employeeName;
	    }

	    public void setEmployeeName(String employeeName) {
	        this.employeeName = employeeName;
	    }

	    public String getDepartment() {
	        return department;
	    }

	    public void setDepartment(String department) {
	        this.department = department;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	}

