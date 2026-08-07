package com.hotel.main;

import java.sql.Date;
import java.util.Scanner;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.CustomerDAO;
import com.hotel.dao.RoomDAO;

public class HotelManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RoomDAO roomDAO = new RoomDAO();

        CustomerDAO customerDAO = new CustomerDAO();

        BookingDAO bookingDAO = new BookingDAO();

        int choice;

        do {

            System.out.println("\n==================================================");
            System.out.println("         HOTEL ROOM BOOKING SYSTEM");
            System.out.println("==================================================");
            System.out.println("1. View All Rooms");
            System.out.println("2. View Available Rooms");
            System.out.println("3. Add Customer");
            System.out.println("4. View Customers");
            System.out.println("5. Book Room");
            System.out.println("6. View Bookings");
            System.out.println("7. Check In");
            System.out.println("8. Check Out");
            System.out.println("9. Exit");
            System.out.println("==================================================");
            System.out.print("Enter Your Choice : ");

            choice = sc.nextInt();

            switch (choice) {

            case 1:

                roomDAO.viewAllRooms();

                break;

            case 2:

                roomDAO.viewAvailableRooms();

                break;

            case 3:

                sc.nextLine();

                System.out.print("Enter Customer Name : ");
                String name = sc.nextLine();

                System.out.print("Enter Phone Number : ");
                String phone = sc.nextLine();

                System.out.print("Enter Email : ");
                String email = sc.nextLine();

                customerDAO.addCustomer(name, phone, email);

                break;

            case 4:

                customerDAO.viewCustomers();

                break;

            case 5:

                customerDAO.viewCustomers();

                System.out.print("\nEnter Customer ID : ");
                int customerId = sc.nextInt();

                roomDAO.viewAvailableRooms();

                System.out.print("\nEnter Room ID : ");
                int roomId = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter Check-In Date (yyyy-mm-dd) : ");
                String inDate = sc.nextLine();

                System.out.print("Enter Check-Out Date (yyyy-mm-dd) : ");
                String outDate = sc.nextLine();

                bookingDAO.bookRoom(
                        customerId,
                        roomId,
                        Date.valueOf(inDate),
                        Date.valueOf(outDate));

                break;
            case 6:

                bookingDAO.viewBookings();

                break;

            case 7:

                bookingDAO.viewBookings();

                System.out.print("\nEnter Booking ID : ");

                int bookingId = sc.nextInt();

                bookingDAO.checkIn(bookingId);

                break;

            case 8:

                bookingDAO.viewBookings();

                System.out.print("\nEnter Booking ID : ");

                int checkoutId = sc.nextInt();

                bookingDAO.checkOut(checkoutId);

                break;

            case 9:

                System.out.println("\n======================================");
                System.out.println("Thank You For Using");
                System.out.println("Hotel Room Booking System");
                System.out.println("Visit Again!");
                System.out.println("======================================");

                break;

            default:

                System.out.println("Invalid Choice... Please Try Again.");

            }

        } while (choice != 9);

        sc.close();

    }

}