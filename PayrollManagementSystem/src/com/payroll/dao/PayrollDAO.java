package com.payroll.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.payroll.db.DBConnection;

public class PayrollDAO {

    // Calculate Salary
    public void calculateSalary(
            int employeeId,
            double basicSalary,
            double hra,
            double allowance,
            double bonus,
            String month) {

        String sql = "INSERT INTO salaries " +
                "(employee_id, basic_salary, hra, allowance, bonus, " +
                "gross_salary, tax, net_salary, salary_month) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            // Gross Salary
            double grossSalary =
                    basicSalary + hra + allowance + bonus;

            // Tax Calculation
            double tax = calculateTax(grossSalary);

            // Net Salary
            double netSalary = grossSalary - tax;

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, employeeId);
            ps.setDouble(2, basicSalary);
            ps.setDouble(3, hra);
            ps.setDouble(4, allowance);
            ps.setDouble(5, bonus);
            ps.setDouble(6, grossSalary);
            ps.setDouble(7, tax);
            ps.setDouble(8, netSalary);
            ps.setString(9, month);

            ps.executeUpdate();

            con.commit();

            System.out.println("\nSalary calculated successfully.");

            System.out.println("Gross Salary : " + grossSalary);
            System.out.println("Tax          : " + tax);
            System.out.println("Net Salary   : " + netSalary);

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Tax Calculation
    private double calculateTax(double grossSalary) {

        // Convert monthly salary to annual salary
        double annualSalary = grossSalary * 12;

        /*
         * Project Tax Rules
         *
         * Up to 12 LPA       -> 0%
         * 12 - 18 LPA        -> 5%
         * 18 - 25 LPA        -> 10%
         * 25 - 35 LPA        -> 15%
         * Above 35 LPA       -> 20%
         */

        double annualTax;

        if (annualSalary <= 1200000) {

            annualTax = 0;

        } else if (annualSalary <= 1800000) {

            annualTax = annualSalary * 0.05;

        } else if (annualSalary <= 2500000) {

            annualTax = annualSalary * 0.10;

        } else if (annualSalary <= 3500000) {

            annualTax = annualSalary * 0.15;

        } else {

            annualTax = annualSalary * 0.20;
        }

        // Convert annual tax to monthly tax
        return annualTax / 12;
    }
    // Generate Payslip
    public void generatePayslip(int employeeId, String month) {

        String sql =
                "SELECT e.employee_id, e.name, e.department, " +
                "e.designation, s.basic_salary, s.hra, " +
                "s.allowance, s.bonus, s.gross_salary, " +
                "s.tax, s.net_salary, s.salary_month " +
                "FROM employees e " +
                "JOIN salaries s " +
                "ON e.employee_id = s.employee_id " +
                "WHERE e.employee_id=? AND s.salary_month=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, month);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n");
                System.out.println("======================================");
                System.out.println("             PAYSLIP");
                System.out.println("======================================");

                System.out.println("Employee ID   : "
                        + rs.getInt("employee_id"));

                System.out.println("Employee Name : "
                        + rs.getString("name"));

                System.out.println("Department    : "
                        + rs.getString("department"));

                System.out.println("Designation   : "
                        + rs.getString("designation"));

                System.out.println("Salary Month  : "
                        + rs.getString("salary_month"));

                System.out.println("--------------------------------------");

                System.out.println("Basic Salary  : "
                        + rs.getDouble("basic_salary"));

                System.out.println("HRA           : "
                        + rs.getDouble("hra"));

                System.out.println("Allowance     : "
                        + rs.getDouble("allowance"));

                System.out.println("Bonus         : "
                        + rs.getDouble("bonus"));

                System.out.println("--------------------------------------");

                System.out.println("Gross Salary  : "
                        + rs.getDouble("gross_salary"));

                System.out.println("Tax Deduction : "
                        + rs.getDouble("tax"));

                System.out.println("Net Salary    : "
                        + rs.getDouble("net_salary"));

                System.out.println("======================================");

            } else {

                System.out.println(
                        "Payslip not found for this employee/month."
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Salary Records
    public void viewSalaryRecords() {

        String sql = "SELECT * FROM salaries";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== SALARY RECORDS ==========");

            while (rs.next()) {

                System.out.println(
                        "Salary ID: " + rs.getInt("salary_id") +
                        " | Employee ID: " + rs.getInt("employee_id") +
                        " | Gross: " + rs.getDouble("gross_salary") +
                        " | Tax: " + rs.getDouble("tax") +
                        " | Net: " + rs.getDouble("net_salary") +
                        " | Month: " + rs.getString("salary_month")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Aggregate Functions
    public void salaryStatistics() {

        String sql =
                "SELECT COUNT(*) AS total_employees, " +
                "SUM(gross_salary) AS total_gross, " +
                "AVG(gross_salary) AS average_salary, " +
                "MAX(gross_salary) AS highest_salary, " +
                "MIN(gross_salary) AS lowest_salary, " +
                "SUM(tax) AS total_tax " +
                "FROM salaries";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {

                System.out.println("\n========== SALARY STATISTICS ==========");

                System.out.println(
                        "Total Salary Records : "
                                + rs.getInt("total_employees")
                );

                System.out.println(
                        "Total Gross Salary   : "
                                + rs.getDouble("total_gross")
                );

                System.out.println(
                        "Average Salary       : "
                                + rs.getDouble("average_salary")
                );

                System.out.println(
                        "Highest Salary       : "
                                + rs.getDouble("highest_salary")
                );

                System.out.println(
                        "Lowest Salary        : "
                                + rs.getDouble("lowest_salary")
                );

                System.out.println(
                        "Total Tax Collected  : "
                                + rs.getDouble("total_tax")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Batch Processing
    public void processPayrollBatch() {

        String selectSql =
                "SELECT salary_id, employee_id " +
                "FROM salaries " +
                "WHERE salary_id NOT IN " +
                "(SELECT salary_id FROM payroll)";

        String insertSql =
                "INSERT INTO payroll " +
                "(employee_id, salary_id, payment_date, payment_status) " +
                "VALUES (?, ?, ?, ?)";

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            PreparedStatement selectPs =
                    con.prepareStatement(selectSql);

            ResultSet rs = selectPs.executeQuery();

            PreparedStatement insertPs =
                    con.prepareStatement(insertSql);

            int count = 0;

            while (rs.next()) {

                int salaryId =
                        rs.getInt("salary_id");

                int employeeId =
                        rs.getInt("employee_id");

                insertPs.setInt(1, employeeId);
                insertPs.setInt(2, salaryId);
                insertPs.setDate(3,
                        new Date(System.currentTimeMillis()));
                insertPs.setString(4, "PAID");

                // Add query to batch
                insertPs.addBatch();

                count++;
            }

            if (count > 0) {

                int[] result =
                        insertPs.executeBatch();

                con.commit();

                System.out.println(
                        count + " payroll records processed successfully."
                );

            } else {

                System.out.println(
                        "No pending payroll records."
                );
            }

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // View Payroll
    public void viewPayroll() {

        String sql =
                "SELECT p.payroll_id, " +
                "e.name, " +
                "p.salary_id, " +
                "p.payment_date, " +
                "p.payment_status " +
                "FROM payroll p " +
                "JOIN employees e " +
                "ON p.employee_id = e.employee_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== PAYROLL ==========");

            while (rs.next()) {

                System.out.println(
                        "Payroll ID: "
                                + rs.getInt("payroll_id") +

                        " | Employee: "
                                + rs.getString("name") +

                        " | Salary ID: "
                                + rs.getInt("salary_id") +

                        " | Payment Date: "
                                + rs.getDate("payment_date") +

                        " | Status: "
                                + rs.getString("payment_status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}