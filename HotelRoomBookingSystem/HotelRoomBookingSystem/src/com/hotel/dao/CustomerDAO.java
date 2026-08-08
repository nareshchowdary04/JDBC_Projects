package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.db.DBConnection;

public class CustomerDAO {

    // Add Customer
    public void addCustomer(String name, String phone, String email) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO customers(customer_name, phone, email) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Customer Added Successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // View All Customers
    public void viewCustomers() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM customers";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n---------------- CUSTOMER DETAILS ----------------");

            System.out.printf("%-8s %-20s %-15s %-30s\n",
                    "ID", "Name", "Phone", "Email");

            while (rs.next()) {

                System.out.printf("%-8d %-20s %-15s %-30s\n",
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("email"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Check Customer Exists
    public boolean customerExists(int customerId) {

        boolean exists = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM customers WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;

    }

    // Get Customer Name
    public String getCustomerName(int customerId) {

        String name = "";

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT customer_name FROM customers WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                name = rs.getString("customer_name");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;

    }

}