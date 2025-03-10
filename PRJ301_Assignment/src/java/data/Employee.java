/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Employee {
    private int EmployeeID;
    private String EmployeeName ;
    private Department Dpt;
    private Employee manager;
    private ArrayList<Employee> staffs = new ArrayList<>();
    private ArrayList<Employee> directstaffs = new ArrayList<>();

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public Department getDpt() {
        return Dpt;
    }

    public void setDpt(Department Dpt) {
        this.Dpt = Dpt;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public ArrayList<Employee> getStaffs() {
        return staffs;
    }

    public void setStaffs(ArrayList<Employee> staffs) {
        this.staffs = staffs;
    }

    public ArrayList<Employee> getDirectstaffs() {
        return directstaffs;
    }

    public void setDirectstaffs(ArrayList<Employee> directstaffs) {
        this.directstaffs = directstaffs;
    }

    
}