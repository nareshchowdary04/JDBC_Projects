package com.leave.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.leave.db.DBConnection;

public class LeaveDAO {

    // Apply Leave (Transaction)
	public void applyLeave(int empId, Date from, Date to, String reason) {

	    Connection con = null;

	    try {

	        con = DBConnection.getConnection();
	        con.setAutoCommit(false);

	        // Calculate leave days
	        long diff = to.getTime() - from.getTime();
	        int leaveDays = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

	        // Check leave balance
	        String checkQuery = "SELECT remaining_leave FROM leave_balance WHERE employee_id=?";

	        PreparedStatement checkPs = con.prepareStatement(checkQuery);
	        checkPs.setInt(1, empId);

	        ResultSet rs = checkPs.executeQuery();

	        if (!rs.next()) {
	            System.out.println("Employee not found.");
	            con.rollback();
	            return;
	        }

	        int remainingLeave = rs.getInt("remaining_leave");

	        if (remainingLeave < leaveDays) {
	            System.out.println("Insufficient Leave Balance.");
	            con.rollback();
	            return;
	        }

	        // Insert leave request
	        String insertLeave = "INSERT INTO leave_requests(employee_id, leave_from, leave_to, reason, status) VALUES(?,?,?,?,?)";

	        PreparedStatement ps1 = con.prepareStatement(insertLeave);

	        ps1.setInt(1, empId);
	        ps1.setDate(2, from);
	        ps1.setDate(3, to);
	        ps1.setString(4, reason);
	        ps1.setString(5, "Pending");

	        ps1.executeUpdate();

	        // Update leave balance
	        String updateBalance = "UPDATE leave_balance SET used_leave = used_leave + ?, remaining_leave = remaining_leave - ? WHERE employee_id=?";

	        PreparedStatement ps2 = con.prepareStatement(updateBalance);

	        ps2.setInt(1, leaveDays);
	        ps2.setInt(2, leaveDays);
	        ps2.setInt(3, empId);

	        int rows = ps2.executeUpdate();

	        if (rows == 0) {
	            System.out.println("Leave balance not updated.");
	            con.rollback();
	            return;
	        }

	        con.commit();

	        // Display updated balance
	        PreparedStatement ps3 = con.prepareStatement(
	                "SELECT total_leave, used_leave, remaining_leave FROM leave_balance WHERE employee_id=?");

	        ps3.setInt(1, empId);

	        ResultSet rs1 = ps3.executeQuery();

	        if (rs1.next()) {

	            System.out.println("\nLeave Applied Successfully.");
	            System.out.println("Leave Days : " + leaveDays);

	            System.out.println("\nUpdated Leave Balance");
	            System.out.println("---------------------------");
	            System.out.println("Total Leave      : " + rs1.getInt("total_leave"));
	            System.out.println("Used Leave       : " + rs1.getInt("used_leave"));
	            System.out.println("Remaining Leave  : " + rs1.getInt("remaining_leave"));
	        }

	    } catch (Exception e) {

	        try {
	            if (con != null)
	                con.rollback();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        e.printStackTrace();

	    } finally {

	        try {
	            if (con != null)
	                con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	public void approveLeave(int leaveId) {

	    String sql = "UPDATE leave_requests SET status='Approved' WHERE leave_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, leaveId);

	        int rows = ps.executeUpdate();

	        if (rows > 0)
	            System.out.println("Leave Approved Successfully.");
	        else
	            System.out.println("Leave ID Not Found.");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void rejectLeave(int leaveId) {

	    String sql = "UPDATE leave_requests SET status='Rejected' WHERE leave_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, leaveId);

	        int rows = ps.executeUpdate();

	        if (rows > 0)
	            System.out.println("Leave Rejected Successfully.");
	        else
	            System.out.println("Leave ID Not Found.");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void viewLeaveBalance(int empId) {

	    String sql = "SELECT * FROM leave_balance WHERE employee_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, empId);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            System.out.println("\nEmployee Leave Balance");
	            System.out.println("-------------------------");
	            System.out.println("Employee ID      : " + rs.getInt("employee_id"));
	            System.out.println("Total Leave      : " + rs.getInt("total_leave"));
	            System.out.println("Used Leave       : " + rs.getInt("used_leave"));
	            System.out.println("Remaining Leave  : " + rs.getInt("remaining_leave"));

	        } else {

	            System.out.println("Employee Not Found.");

	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}