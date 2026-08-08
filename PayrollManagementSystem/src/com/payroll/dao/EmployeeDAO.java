package com.payroll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.payroll.db.DBConnection;
import com.payroll.model.Employee;

public class EmployeeDAO {

    // Add Employee
    public void addEmployee(Employee emp) {

        String sql = "INSERT INTO employees " +
                "(name, email, phone, department, designation, joining_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setString(4, emp.getDepartment());
            ps.setString(5, emp.getDesignation());
            ps.setDate(6, emp.getJoiningDate());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee registered successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Employees
    public void viewEmployees() {

        String sql = "SELECT * FROM employees";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== EMPLOYEE LIST ==========");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("employee_id") +
                        " | Name: " + rs.getString("name") +
                        " | Email: " + rs.getString("email") +
                        " | Phone: " + rs.getString("phone") +
                        " | Department: " + rs.getString("department") +
                        " | Designation: " + rs.getString("designation") +
                        " | Joining Date: " + rs.getDate("joining_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search Employee
    public void searchEmployee(int employeeId) {

        String sql = "SELECT * FROM employees WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nEmployee Found");
                System.out.println("----------------------");

                System.out.println("ID: "
                        + rs.getInt("employee_id"));

                System.out.println("Name: "
                        + rs.getString("name"));

                System.out.println("Email: "
                        + rs.getString("email"));

                System.out.println("Phone: "
                        + rs.getString("phone"));

                System.out.println("Department: "
                        + rs.getString("department"));

                System.out.println("Designation: "
                        + rs.getString("designation"));

            } else {

                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Employee
    public void deleteEmployee(int employeeId) {

        String sql = "DELETE FROM employees WHERE employee_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}