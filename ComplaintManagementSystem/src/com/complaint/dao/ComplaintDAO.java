package com.complaint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.complaint.db.DBConnection;
import com.complaint.model.Complaint;

public class ComplaintDAO {

    // Register Complaint
    public void registerComplaint(Complaint complaint) {

        String sql =
                "INSERT INTO complaints(user_id, complaint_text, status) "
                + "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, complaint.getUserId());
            ps.setString(2, complaint.getComplaintText());
            ps.setString(3, "REGISTERED");

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Complaint registered successfully.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Assign Officer
    public void assignOfficer(int complaintId, int officerId) {

        String sql =
                "UPDATE complaints SET officer_id = ?, status = ? "
                + "WHERE complaint_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, officerId);
            ps.setString(2, "ASSIGNED");
            ps.setInt(3, complaintId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Officer assigned successfully.");
            } else {
                System.out.println("Complaint not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Status
    public void updateStatus(int complaintId, String status) {

        String sql =
                "UPDATE complaints SET status = ? "
                + "WHERE complaint_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, complaintId);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Complaint status updated successfully.");
            } else {
                System.out.println("Complaint not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Resolution
    public void viewResolution(int complaintId) {

        String sql =
                "SELECT c.complaint_id, u.name AS user_name, "
                + "c.complaint_text, c.status, "
                + "o.name AS officer_name, o.department "
                + "FROM complaints c "
                + "JOIN users u ON c.user_id = u.user_id "
                + "LEFT JOIN officers o ON c.officer_id = o.officer_id "
                + "WHERE c.complaint_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, complaintId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n----- COMPLAINT DETAILS -----");

                System.out.println(
                        "Complaint ID: "
                        + rs.getInt("complaint_id")
                );

                System.out.println(
                        "Citizen: "
                        + rs.getString("user_name")
                );

                System.out.println(
                        "Complaint: "
                        + rs.getString("complaint_text")
                );

                System.out.println(
                        "Status: "
                        + rs.getString("status")
                );

                System.out.println(
                        "Officer: "
                        + rs.getString("officer_name")
                );

                System.out.println(
                        "Department: "
                        + rs.getString("department")
                );

            } else {

                System.out.println("Complaint not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View All Complaints
    public void viewAllComplaints() {

        String sql =
                "SELECT c.complaint_id, u.name AS user_name, "
                + "c.complaint_text, c.status, "
                + "o.name AS officer_name "
                + "FROM complaints c "
                + "JOIN users u ON c.user_id = u.user_id "
                + "LEFT JOIN officers o "
                + "ON c.officer_id = o.officer_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n----- ALL COMPLAINTS -----");

            while (rs.next()) {

                System.out.println(
                        "Complaint ID: "
                        + rs.getInt("complaint_id")
                );

                System.out.println(
                        "Citizen: "
                        + rs.getString("user_name")
                );

                System.out.println(
                        "Complaint: "
                        + rs.getString("complaint_text")
                );

                System.out.println(
                        "Status: "
                        + rs.getString("status")
                );

                System.out.println(
                        "Officer: "
                        + rs.getString("officer_name")
                );

                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}