package com.complaint.main;

import java.util.Scanner;

import com.complaint.dao.ComplaintDAO;
import com.complaint.dao.OfficerDAO;
import com.complaint.dao.UserDAO;
import com.complaint.model.Complaint;
import com.complaint.model.Officer;
import com.complaint.model.User;

public class ComplaintManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        OfficerDAO officerDAO = new OfficerDAO();
        ComplaintDAO complaintDAO = new ComplaintDAO();

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("   COMPLAINT MANAGEMENT SYSTEM");
            System.out.println("=================================");

            System.out.println("1. Register User");
            System.out.println("2. Register Complaint");
            System.out.println("3. Add Officer");
            System.out.println("4. Assign Officer");
            System.out.println("5. Update Complaint Status");
            System.out.println("6. View Complaint Resolution");
            System.out.println("7. View All Complaints");
            System.out.println("8. View Users");
            System.out.println("9. View Officers");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\n--- REGISTER USER ---");

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    User user =
                            new User(name, email, phone);

                    userDAO.registerUser(user);

                    break;

                case 2:

                    System.out.println("\n--- REGISTER COMPLAINT ---");

                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Complaint: ");
                    String complaintText = sc.nextLine();

                    Complaint complaint =
                            new Complaint(userId, complaintText);

                    complaintDAO.registerComplaint(complaint);

                    break;

                case 3:

                    System.out.println("\n--- ADD OFFICER ---");

                    System.out.print("Enter Officer Name: ");
                    String officerName = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();

                    Officer officer =
                            new Officer(officerName, department);

                    officerDAO.addOfficer(officer);

                    break;

                case 4:

                    System.out.println("\n--- ASSIGN OFFICER ---");

                    System.out.print("Enter Complaint ID: ");
                    int complaintId = sc.nextInt();

                    System.out.print("Enter Officer ID: ");
                    int officerId = sc.nextInt();

                    complaintDAO.assignOfficer(
                            complaintId,
                            officerId
                    );

                    break;

                case 5:

                    System.out.println("\n--- UPDATE STATUS ---");

                    System.out.print("Enter Complaint ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.println("\nSelect Status:");

                    System.out.println("1. REGISTERED");
                    System.out.println("2. ASSIGNED");
                    System.out.println("3. IN_PROGRESS");
                    System.out.println("4. RESOLVED");
                    System.out.println("5. REJECTED");

                    System.out.print("Enter status choice: ");

                    int statusChoice = sc.nextInt();

                    String status;

                    switch (statusChoice) {

                        case 1:
                            status = "REGISTERED";
                            break;

                        case 2:
                            status = "ASSIGNED";
                            break;

                        case 3:
                            status = "IN_PROGRESS";
                            break;

                        case 4:
                            status = "RESOLVED";
                            break;

                        case 5:
                            status = "REJECTED";
                            break;

                        default:
                            System.out.println("Invalid status.");
                            continue;
                    }

                    complaintDAO.updateStatus(id, status);

                    break;

                case 6:

                    System.out.println("\n--- VIEW RESOLUTION ---");

                    System.out.print("Enter Complaint ID: ");
                    int resolutionId = sc.nextInt();

                    complaintDAO.viewResolution(resolutionId);

                    break;

                case 7:

                    complaintDAO.viewAllComplaints();

                    break;

                case 8:

                    userDAO.viewUsers();

                    break;

                case 9:

                    officerDAO.viewOfficers();

                    break;

                case 0:

                    System.out.println(
                            "Thank you for using Complaint Management System."
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }

        } while (choice != 0);

        sc.close();
    }
}