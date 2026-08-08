package com.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.complaint.db.DBConnection;
import com.complaint.model.User;

public class UserDAO {

    // Register User
    public void registerUser(User user) {

        String sql = "INSERT INTO users(name, email, phone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("User registered successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Users
    public void viewUsers() {

        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- USERS -----");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("user_id")
                        + " | Name: " + rs.getString("name")
                        + " | Email: " + rs.getString("email")
                        + " | Phone: " + rs.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}