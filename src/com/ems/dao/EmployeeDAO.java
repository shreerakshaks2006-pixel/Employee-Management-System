package com.ems.dao;

import com.ems.db.DBConnection;
import com.ems.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object — all SQL operations on the employees table.
 */
public class EmployeeDAO {

    // ── Add ───────────────────────────────────────────────────────────────────

    /**
     * Inserts a new employee and returns the generated ID, or -1 on failure.
     */
    public int addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, email, phone, department, designation, salary, hire_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setString(4, emp.getDepartment());
            ps.setString(5, emp.getDesignation());
            ps.setDouble(6, emp.getSalary());
            ps.setDate  (7, Date.valueOf(emp.getHireDate()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("[DAO] addEmployee failed: " + e.getMessage());
        }
        return -1;
    }

    // ── View All ──────────────────────────────────────────────────────────────

    /** Returns every employee record ordered by id. */
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("[DAO] getAllEmployees failed: " + e.getMessage());
        }
        return list;
    }

    // ── Search by ID ──────────────────────────────────────────────────────────

    /** Returns the employee with the given id, or null if not found. */
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("[DAO] getEmployeeById failed: " + e.getMessage());
        }
        return null;
    }

    // ── Search by Name ────────────────────────────────────────────────────────

    /** Returns all employees whose name contains the given keyword (case-insensitive). */
    public List<Employee> searchByName(String keyword) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ? ORDER BY name";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAO] searchByName failed: " + e.getMessage());
        }
        return list;
    }

    // ── Search by Department ──────────────────────────────────────────────────

    /** Returns all employees in the given department (case-insensitive). */
    public List<Employee> searchByDepartment(String department) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department LIKE ? ORDER BY name";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + department + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("[DAO] searchByDepartment failed: " + e.getMessage());
        }
        return list;
    }

    // ── Update ────────────────────────────────────────────────────────────────

    /**
     * Updates all fields of an existing employee.
     * Returns true if exactly one row was updated.
     */
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, email=?, phone=?, department=?, "
                   + "designation=?, salary=?, hire_date=? WHERE id=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setString(4, emp.getDepartment());
            ps.setString(5, emp.getDesignation());
            ps.setDouble(6, emp.getSalary());
            ps.setDate  (7, Date.valueOf(emp.getHireDate()));
            ps.setInt   (8, emp.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[DAO] updateEmployee failed: " + e.getMessage());
        }
        return false;
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    /**
     * Deletes the employee with the given id.
     * Returns true if exactly one row was deleted.
     */
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[DAO] deleteEmployee failed: " + e.getMessage());
        }
        return false;
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt   ("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("department"),
            rs.getString("designation"),
            rs.getDouble("salary"),
            rs.getDate  ("hire_date").toString()
        );
    }
}
