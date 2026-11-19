package com.ems.main;

import com.ems.dao.EmployeeDAO;
import com.ems.db.DBConnection;
import com.ems.model.Employee;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point — menu-driven CLI for the Employee Management System.
 */
public class EmployeeManagementSystem {

    private static final EmployeeDAO dao     = new EmployeeDAO();
    private static final Scanner     scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     Employee Management System  v1.0     ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Establish connection once at startup
        DBConnection.getConnection();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1  -> addEmployee();
                case 2  -> viewAllEmployees();
                case 3  -> searchEmployee();
                case 4  -> updateEmployee();
                case 5  -> deleteEmployee();
                case 6  -> { running = false; }
                default -> System.out.println("  Invalid choice. Please enter 1-6.");
            }
        }

        DBConnection.closeConnection();
        System.out.println("\n  Thank you for using the Employee Management System. Goodbye!");
        scanner.close();
    }

    // ── Menu ──────────────────────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│           MAIN MENU         │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│  1. Add Employee             │");
        System.out.println("│  2. View All Employees       │");
        System.out.println("│  3. Search Employee          │");
        System.out.println("│  4. Update Employee          │");
        System.out.println("│  5. Delete Employee          │");
        System.out.println("│  6. Exit                     │");
        System.out.println("└─────────────────────────────┘");
    }

    // ── 1. Add ────────────────────────────────────────────────────────────────

    private static void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        String name        = readString("Name        : ");
        String email       = readString("Email       : ");
        String phone       = readString("Phone       : ");
        String department  = readString("Department  : ");
        String designation = readString("Designation : ");
        double salary      = readDouble("Salary      : ");
        String hireDate    = readDate  ("Hire Date (YYYY-MM-DD): ");

        Employee emp = new Employee(name, email, phone, department, designation, salary, hireDate);
        int newId = dao.addEmployee(emp);
        if (newId != -1) {
            System.out.println("  Employee added successfully! Assigned ID: " + newId);
        } else {
            System.out.println("  Failed to add employee. Check if the email is already registered.");
        }
    }

    // ── 2. View All ───────────────────────────────────────────────────────────

    private static void viewAllEmployees() {
        System.out.println("\n--- All Employees ---");
        List<Employee> list = dao.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("  No employees found.");
            return;
        }
        printTableHeader();
        list.forEach(e -> System.out.println(e.toTableRow()));
        System.out.println("  Total: " + list.size() + " employee(s).");
    }

    // ── 3. Search ─────────────────────────────────────────────────────────────

    private static void searchEmployee() {
        System.out.println("\n--- Search Employee ---");
        System.out.println("  1. Search by ID");
        System.out.println("  2. Search by Name");
        System.out.println("  3. Search by Department");
        int choice = readInt("Choose search type: ");

        switch (choice) {
            case 1 -> {
                int id = readInt("Enter Employee ID: ");
                Employee emp = dao.getEmployeeById(id);
                if (emp != null) System.out.println(emp);
                else System.out.println("  No employee found with ID " + id + ".");
            }
            case 2 -> {
                String name = readString("Enter Name (or part of it): ");
                List<Employee> results = dao.searchByName(name);
                if (results.isEmpty()) System.out.println("  No employees found matching '" + name + "'.");
                else { printTableHeader(); results.forEach(e -> System.out.println(e.toTableRow())); }
            }
            case 3 -> {
                String dept = readString("Enter Department (or part of it): ");
                List<Employee> results = dao.searchByDepartment(dept);
                if (results.isEmpty()) System.out.println("  No employees found in '" + dept + "'.");
                else { printTableHeader(); results.forEach(e -> System.out.println(e.toTableRow())); }
            }
            default -> System.out.println("  Invalid choice.");
        }
    }

    // ── 4. Update ─────────────────────────────────────────────────────────────

    private static void updateEmployee() {
        System.out.println("\n--- Update Employee ---");
        int id = readInt("Enter Employee ID to update: ");
        Employee existing = dao.getEmployeeById(id);
        if (existing == null) {
            System.out.println("  No employee found with ID " + id + ".");
            return;
        }
        System.out.println("  Current record:");
        System.out.println(existing);
        System.out.println("  Enter new values (press Enter to keep current value):");

        String name        = readStringOrDefault("Name        [" + existing.getName()        + "]: ", existing.getName());
        String email       = readStringOrDefault("Email       [" + existing.getEmail()       + "]: ", existing.getEmail());
        String phone       = readStringOrDefault("Phone       [" + existing.getPhone()       + "]: ", existing.getPhone());
        String department  = readStringOrDefault("Department  [" + existing.getDepartment()  + "]: ", existing.getDepartment());
        String designation = readStringOrDefault("Designation [" + existing.getDesignation() + "]: ", existing.getDesignation());
        double salary      = readDoubleOrDefault ("Salary      [" + existing.getSalary()      + "]: ", existing.getSalary());
        String hireDate    = readStringOrDefault("Hire Date   [" + existing.getHireDate()    + "]: ", existing.getHireDate());

        Employee updated = new Employee(id, name, email, phone, department, designation, salary, hireDate);
        if (dao.updateEmployee(updated)) {
            System.out.println("  Employee updated successfully.");
        } else {
            System.out.println("  Update failed. The email may already be in use by another employee.");
        }
    }

    // ── 5. Delete ─────────────────────────────────────────────────────────────

    private static void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        int id = readInt("Enter Employee ID to delete: ");
        Employee emp = dao.getEmployeeById(id);
        if (emp == null) {
            System.out.println("  No employee found with ID " + id + ".");
            return;
        }
        System.out.println("  Record to be deleted:");
        System.out.println(emp);
        String confirm = readString("Are you sure you want to delete this employee? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            if (dao.deleteEmployee(id)) System.out.println("  Employee deleted successfully.");
            else System.out.println("  Deletion failed.");
        } else {
            System.out.println("  Deletion cancelled.");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void printTableHeader() {
        System.out.printf("%-4s %-20s %-28s %-15s %-15s %-18s %10s  %s%n",
            "ID", "Name", "Email", "Phone", "Department", "Designation", "Salary", "Hire Date");
        System.out.println("-".repeat(120));
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String readStringOrDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private static double readDoubleOrDefault(String prompt, double defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number; keeping existing value.");
            return defaultValue;
        }
    }

    /** Keeps prompting until a valid YYYY-MM-DD date is entered. */
    private static String readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) return input;
            System.out.println("  Invalid format. Please use YYYY-MM-DD (e.g. 2023-06-15).");
        }
    }
}
