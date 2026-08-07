package com.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.hotel.db.DBConnection;
public class BookingDAO {
	// Book Room (Transaction)
	public void bookRoom(int customerId, int roomId,
	        Date checkIn, Date checkOut) {

	    Connection con = null;

	    try {

	        con = DBConnection.getConnection();

	        con.setAutoCommit(false);

	        CustomerDAO customerDAO = new CustomerDAO();

	        RoomDAO roomDAO = new RoomDAO();

	        // Customer Validation
	        if (!customerDAO.customerExists(customerId)) {

	            System.out.println("Customer ID Not Found.");

	            return;

	        }

	        // Room Validation
	        if (!roomDAO.isRoomAvailable(roomId)) {

	            System.out.println("Room is Already Booked.");

	            return;

	        }

	        // Calculate Days
	        LocalDate inDate = checkIn.toLocalDate();

	        LocalDate outDate = checkOut.toLocalDate();

	        long days = ChronoUnit.DAYS.between(inDate, outDate);

	        if (days <= 0) {

	            System.out.println("Invalid Check-Out Date.");

	            return;

	        }

	        // Room Price
	        double roomPrice = roomDAO.getRoomPrice(roomId);

	        double totalAmount = roomPrice * days;

	        String sql = "INSERT INTO bookings(customer_id, room_id, booking_date,"
	                + "check_in, check_out, total_days, total_amount, status)"
	                + " VALUES(?,?,?,?,?,?,?,?)";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, customerId);

	        ps.setInt(2, roomId);

	        ps.setDate(3, Date.valueOf(LocalDate.now()));

	        ps.setDate(4, checkIn);

	        ps.setDate(5, checkOut);

	        ps.setInt(6, (int) days);

	        ps.setDouble(7, totalAmount);

	        ps.setString(8, "Booked");

	        int rows = ps.executeUpdate();

	        if (rows > 0) {

	            String updateRoom =
	                    "UPDATE rooms SET status='Booked' WHERE room_id=?";

	            PreparedStatement ps2 =
	                    con.prepareStatement(updateRoom);

	            ps2.setInt(1, roomId);

	            ps2.executeUpdate();

	            con.commit();

	            System.out.println();

	            System.out.println("Room Booked Successfully.");

	            System.out.println("Booking Amount : " + totalAmount);

	        } else {

	            con.rollback();

	            System.out.println("Booking Failed.");

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

	                con.setAutoCommit(true);

	                con.close();

	            }

	        } catch (Exception e) {

	            e.printStackTrace();

	        }

	    }

	}
	// View All Bookings
	public void viewBookings() {

	    try {

	        Connection con = DBConnection.getConnection();

	        String sql = "SELECT b.booking_id, c.customer_name, r.room_number, "
	                + "b.check_in, b.check_out, b.total_days, "
	                + "b.total_amount, b.status "
	                + "FROM bookings b "
	                + "JOIN customers c ON b.customer_id = c.customer_id "
	                + "JOIN rooms r ON b.room_id = r.room_id";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ResultSet rs = ps.executeQuery();

	        System.out.println("\n==================== BOOKING DETAILS ====================");

	        System.out.printf("%-5s %-15s %-10s %-12s %-12s %-6s %-10s %-12s\n",
	                "ID", "Customer", "Room", "Check-In",
	                "Check-Out", "Days", "Amount", "Status");

	        while (rs.next()) {

	            System.out.printf("%-5d %-15s %-10s %-12s %-12s %-6d %-10.2f %-12s\n",
	                    rs.getInt("booking_id"),
	                    rs.getString("customer_name"),
	                    rs.getString("room_number"),
	                    rs.getDate("check_in"),
	                    rs.getDate("check_out"),
	                    rs.getInt("total_days"),
	                    rs.getDouble("total_amount"),
	                    rs.getString("status"));

	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	}
	// Check In
	public void checkIn(int bookingId) {

	    try {

	        Connection con = DBConnection.getConnection();

	        String checkSql =
	                "SELECT status FROM bookings WHERE booking_id=?";

	        PreparedStatement checkPs =
	                con.prepareStatement(checkSql);

	        checkPs.setInt(1, bookingId);

	        ResultSet rs = checkPs.executeQuery();
	        
	        if (!rs.isBeforeFirst()) {
	            System.out.println("No bookings found.");
	            return;
	        }

	        if (rs.next()) {

	            String status = rs.getString("status");

	            if (status.equalsIgnoreCase("Checked-In")) {

	                System.out.println("Customer Already Checked-In.");

	                return;

	            }

	            if (status.equalsIgnoreCase("Checked-Out")) {

	                System.out.println("Booking Already Completed.");

	                return;

	            }

	        } else {

	            System.out.println("Booking ID Not Found.");

	            return;

	        }

	        String updateSql =
	                "UPDATE bookings SET status='Checked-In' WHERE booking_id=?";

	        PreparedStatement ps =
	                con.prepareStatement(updateSql);

	        ps.setInt(1, bookingId);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {

	            System.out.println("Check-In Successful.");

	        }

	        rs.close();
	        checkPs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {

	        e.printStackTrace();

	    }

	}
	// Check Out (Transaction)
	public void checkOut(int bookingId) {

	    Connection con = null;

	    try {

	        con = DBConnection.getConnection();

	        con.setAutoCommit(false);

	        // Get Booking Details
	        String selectQuery =
	                "SELECT room_id,total_amount,status FROM bookings WHERE booking_id=?";

	        PreparedStatement ps =
	                con.prepareStatement(selectQuery);

	        ps.setInt(1, bookingId);

	        ResultSet rs = ps.executeQuery();

	        if (!rs.next()) {

	            System.out.println("Booking ID Not Found.");

	            return;

	        }

	        int roomId = rs.getInt("room_id");

	        double bill = rs.getDouble("total_amount");

	        String status = rs.getString("status");

	        if (status.equalsIgnoreCase("Checked-Out")) {

	            System.out.println("Customer Already Checked-Out.");

	            return;

	        }

	        // Update Booking Status

	        String bookingUpdate =
	                "UPDATE bookings SET status='Checked-Out' WHERE booking_id=?";

	        PreparedStatement ps1 =
	                con.prepareStatement(bookingUpdate);

	        ps1.setInt(1, bookingId);

	        ps1.executeUpdate();

	        // Make Room Available

	        String roomUpdate =
	                "UPDATE rooms SET status='Available' WHERE room_id=?";

	        PreparedStatement ps2 =
	                con.prepareStatement(roomUpdate);

	        ps2.setInt(1, roomId);

	        ps2.executeUpdate();

	        con.commit();

	        System.out.println();

	        System.out.println("===================================");

	        System.out.println("CHECK-OUT SUCCESSFUL");

	        System.out.println("-----------------------------------");

	        System.out.println("Booking ID : " + bookingId);

	        System.out.println("Room ID    : " + roomId);

	        System.out.println("Total Bill : ₹" + bill);

	        System.out.println("-----------------------------------");

	        System.out.println("Thank You... Visit Again.");

	        System.out.println("===================================");

	        rs.close();

	        ps.close();

	        ps1.close();

	        ps2.close();

	        con.close();

	    }

	    catch (Exception e) {

	        try {

	            if (con != null) {

	                con.rollback();

	            }

	        }

	        catch (Exception ex) {

	            ex.printStackTrace();

	        }finally {
	            try {
	                if (con != null) {
	                    con.setAutoCommit(true);
	                    con.close();
	                }
	            } catch (Exception ex) {
	                e.printStackTrace();
	            }
	        }

	        e.printStackTrace();

	    }

	}
	
}
