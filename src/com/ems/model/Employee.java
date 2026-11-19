package com.ems.model;

/**
 * Represents an employee record.
 */
public class Employee {

    private int    id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private double salary;
    private String hireDate;   // stored as "YYYY-MM-DD"

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Used when creating a new employee (id assigned by DB). */
    public Employee(String name, String email, String phone,
                    String department, String designation,
                    double salary, String hireDate) {
        this.name        = name;
        this.email       = email;
        this.phone       = phone;
        this.department  = department;
        this.designation = designation;
        this.salary      = salary;
        this.hireDate    = hireDate;
    }

    /** Used when reading a record from the database. */
    public Employee(int id, String name, String email, String phone,
                    String department, String designation,
                    double salary, String hireDate) {
        this(name, email, phone, department, designation, salary, hireDate);
        this.id = id;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getName()              { return name; }
    public void   setName(String name)   { this.name = name; }

    public String getEmail()               { return email; }
    public void   setEmail(String email)   { this.email = email; }

    public String getPhone()               { return phone; }
    public void   setPhone(String phone)   { this.phone = phone; }

    public String getDepartment()                    { return department; }
    public void   setDepartment(String department)   { this.department = department; }

    public String getDesignation()                     { return designation; }
    public void   setDesignation(String designation)   { this.designation = designation; }

    public double getSalary()                { return salary; }
    public void   setSalary(double salary)   { this.salary = salary; }

    public String getHireDate()                  { return hireDate; }
    public void   setHireDate(String hireDate)   { this.hireDate = hireDate; }

    // ── Display ───────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format(
            "┌─────────────────────────────────────┐%n" +
            "  ID          : %d%n" +
            "  Name        : %s%n" +
            "  Email       : %s%n" +
            "  Phone       : %s%n" +
            "  Department  : %s%n" +
            "  Designation : %s%n" +
            "  Salary      : %.2f%n" +
            "  Hire Date   : %s%n" +
            "└─────────────────────────────────────┘",
            id, name, email, phone, department, designation, salary, hireDate
        );
    }

    /** One-line summary for list views. */
    public String toTableRow() {
        return String.format("%-4d %-20s %-28s %-15s %-15s %-18s %10.2f  %s",
            id, name, email, phone, department, designation, salary, hireDate);
    }
}
