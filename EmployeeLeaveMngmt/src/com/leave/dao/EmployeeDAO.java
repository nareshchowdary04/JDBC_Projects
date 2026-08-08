package com.leave.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.leave.db.DBConnection;
import com.leave.model.Employee;

public class EmployeeDAO {

    public void registerEmployee(Employee emp) {

        String sql="insert into employees(employee_name,department,email,phone) values(?,?,?,?)";

        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,emp.getEmployeeName());
            ps.setString(2,emp.getDepartment());
            ps.setString(3,emp.getEmail());
            ps.setString(4,emp.getPhone());

            int i=ps.executeUpdate();

            System.out.println(i+" Employee Registered");

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

    public void displayEmployees(){

        try(Connection con=DBConnection.getConnection();
            Statement st=con.createStatement()){

            ResultSet rs=st.executeQuery("select * from employees");

            while(rs.next()){

                System.out.println(

                        rs.getInt(1)+" "

                                +rs.getString(2)+" "

                                +rs.getString(3)+" "

                                +rs.getString(4)+" "

                                +rs.getString(5)

                );

            }

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

}