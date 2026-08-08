package com.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.complaint.db.DBConnection;
import com.complaint.model.Officer;

public class OfficerDAO {

    // Add Officer
    public void addOfficer(Officer officer) {

        String sql =
                "INSERT INTO officers(name, department) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, officer.getName());
            ps.setString(2, officer.getDepartment());

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Officer added successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Officers
    public void viewOfficers() {

        String sql = "SELECT * FROM officers";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- OFFICERS -----");

            while (rs.next()) {

                System.out.println(
                        "ID: " + rs.getInt("officer_id")
                        + " | Name: " + rs.getString("name")
                        + " | Department: "
                        + rs.getString("department")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
