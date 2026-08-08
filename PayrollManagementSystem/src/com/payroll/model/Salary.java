package com.payroll.model;

public class Salary {

    private int salaryId;
    private int employeeId;

    private double basicSalary;
    private double hra;
    private double allowance;
    private double bonus;

    private double grossSalary;
    private double tax;
    private double netSalary;

    private String salaryMonth;

    public Salary() {
    }

    public Salary(int employeeId,
                  double basicSalary,
                  double hra,
                  double allowance,
                  double bonus,
                  String salaryMonth) {

        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.bonus = bonus;
        this.salaryMonth = salaryMonth;
    }

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }
}