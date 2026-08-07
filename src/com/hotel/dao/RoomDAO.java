package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hotel.db.DBConnection;

public class RoomDAO {

    // Display All Rooms
    public void viewAllRooms() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM rooms";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n------------------------------ROOM DETAILS------------------------------");

            System.out.printf("%-8s %-12s %-15s %-12s %-12s\n",
                    "ID", "Room No", "Type", "Price", "Status");

            while (rs.next()) {

                System.out.printf("%-8d %-12s %-15s %-12.2f %-12s\n",
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_day"),
                        rs.getString("status"));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Display Only Available Rooms
    public void viewAvailableRooms() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM rooms WHERE status='Available'";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n----------------AVAILABLE ROOMS----------------");

            System.out.printf("%-8s %-12s %-15s %-12s\n",
                    "ID", "Room No", "Type", "Price");

            while (rs.next()) {

                System.out.printf("%-8d %-12s %-15s %-12.2f\n",
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_day"));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // Get Room Price
    public double getRoomPrice(int roomId) {

        double price = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT price_per_day FROM rooms WHERE room_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                price = rs.getDouble("price_per_day");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return price;

    }

    // Check Room Availability
    public boolean isRoomAvailable(int roomId) {

        boolean available = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT status FROM rooms WHERE room_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (rs.getString("status").equalsIgnoreCase("Available")) {

                    available = true;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return available;

    }

    // Update Room Status
    public void updateRoomStatus(int roomId, String status) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE rooms SET status=? WHERE room_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);

            ps.setInt(2, roomId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Room Status Updated Successfully.");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}