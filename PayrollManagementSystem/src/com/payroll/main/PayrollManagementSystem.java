package com.payroll.main;

import java.sql.Date;
import java.util.Scanner;

import com.payroll.dao.EmployeeDAO;
import com.payroll.dao.PayrollDAO;
import com.payroll.model.Employee;

public class PayrollManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        PayrollDAO payrollDAO = new PayrollDAO();

        while (true) {

            System.out.println("\n");
            System.out.println("======================================");
            System.out.println("       PAYROLL MANAGEMENT SYSTEM");
            System.out.println("======================================");

            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");

            System.out.println("--------------------------------------");

            System.out.println("5. Calculate Salary");
            System.out.println("6. View Salary Records");
            System.out.println("7. Generate Payslip");
            System.out.println("8. Salary Statistics");

            System.out.println("--------------------------------------");

            System.out.println("9. Process Payroll Batch");
            System.out.println("10. View Payroll");

            System.out.println("--------------------------------------");

            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\n========== ADD EMPLOYEE ==========");

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter Designation: ");
                    String designation = sc.nextLine();

                    System.out.print(
                            "Enter Joining Date (yyyy-mm-dd): "
                    );

                    Date joiningDate =
                            Date.valueOf(sc.nextLine());

                    Employee emp = new Employee(
                            name,
                            email,
                            phone,
                            department,
                            designation,
                            joiningDate
                    );

                    employeeDAO.addEmployee(emp);

                    break;

                case 2:

                    employeeDAO.viewEmployees();

                    break;

                case 3:

                    System.out.print(
                            "Enter Employee ID: "
                    );

                    int searchId = sc.nextInt();

                    employeeDAO.searchEmployee(searchId);

                    break;

                case 4:

                    System.out.print(
                            "Enter Employee ID to delete: "
                    );

                    int deleteId = sc.nextInt();

                    employeeDAO.deleteEmployee(deleteId);

                    break;

                case 5:

                    System.out.println(
                            "\n========== SALARY CALCULATION =========="
                    );

                    System.out.print(
                            "Enter Employee ID: "
                    );

                    int employeeId = sc.nextInt();

                    System.out.print(
                            "Enter Basic Salary: "
                    );

                    double basicSalary = sc.nextDouble();

                    System.out.print(
                            "Enter HRA: "
                    );

                    double hra = sc.nextDouble();

                    System.out.print(
                            "Enter Allowance: "
                    );

                    double allowance = sc.nextDouble();

                    System.out.print(
                            "Enter Bonus: "
                    );

                    double bonus = sc.nextDouble();

                    sc.nextLine();

                    System.out.print(
                            "Enter Salary Month (e.g. August-2026): "
                    );

                    String month = sc.nextLine();

                    payrollDAO.calculateSalary(
                            employeeId,
                            basicSalary,
                            hra,
                            allowance,
                            bonus,
                            month
                    );

                    break;

                case 6:

                    payrollDAO.viewSalaryRecords();

                    break;

                case 7:

                    System.out.println(
                            "\n========== GENERATE PAYSLIP =========="
                    );

                    System.out.print(
                            "Enter Employee ID: "
                    );

                    int payslipEmployeeId =
                            sc.nextInt();

                    sc.nextLine();

                    System.out.print(
                            "Enter Salary Month: "
                    );

                    String payslipMonth =
                            sc.nextLine();

                    payrollDAO.generatePayslip(
                            payslipEmployeeId,
                            payslipMonth
                    );

                    break;

                case 8:

                    payrollDAO.salaryStatistics();

                    break;

                case 9:

                    System.out.println(
                            "\n========== BATCH PAYROLL PROCESSING =========="
                    );

                    payrollDAO.processPayrollBatch();

                    break;

                case 10:

                    payrollDAO.viewPayroll();

                    break;

                case 0:

                    System.out.println(
                            "Thank you for using Payroll Management System."
                    );

                    sc.close();

                    System.exit(0);

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }
}