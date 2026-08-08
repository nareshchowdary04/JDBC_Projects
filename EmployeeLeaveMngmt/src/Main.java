import java.sql.Date;
import java.util.Scanner;

import com.leave.dao.EmployeeDAO;
import com.leave.dao.LeaveDAO;
import com.leave.model.Employee;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        LeaveDAO leaveDAO = new LeaveDAO();

        while (true) {

            System.out.println("\n==============================");
            System.out.println(" EMPLOYEE LEAVE MANAGEMENT ");
            System.out.println("==============================");
            System.out.println("1. Register Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Apply Leave");
            System.out.println("4. Approve Leave");
            System.out.println("5. Reject Leave");
            System.out.println("6. View Leave Balance");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            case 1:

                System.out.print("Enter Employee Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Department: ");
                String dept = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();

                Employee emp = new Employee(name, dept, email, phone);

                employeeDAO.registerEmployee(emp);

                break;

            case 2:

                employeeDAO.displayEmployees();

                break;

            case 3:

                System.out.print("Enter Employee ID: ");
                int empId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Leave From (yyyy-mm-dd): ");
                String from = sc.nextLine();

                System.out.print("Enter Leave To (yyyy-mm-dd): ");
                String to = sc.nextLine();

                System.out.print("Enter Reason: ");
                String reason = sc.nextLine();

                leaveDAO.applyLeave(
                        empId,
                        Date.valueOf(from),
                        Date.valueOf(to),
                        reason);

                break;

            case 4:

                System.out.print("Enter Leave ID: ");
                int approveId = sc.nextInt();

                leaveDAO.approveLeave(approveId);

                break;

            case 5:

                System.out.print("Enter Leave ID: ");
                int rejectId = sc.nextInt();

                leaveDAO.rejectLeave(rejectId);

                break;

            case 6:

                System.out.print("Enter Employee ID: ");
                int balanceId = sc.nextInt();

                leaveDAO.viewLeaveBalance(balanceId);

                break;

            case 7:

                System.out.println("Thank You!");
                sc.close();
                System.exit(0);

            default:

                System.out.println("Invalid Choice!");

            }

        }

    }

}