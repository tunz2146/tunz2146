package com.Tunznews.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.Tunznews.model.User;

public class UserDAO {

    // 🟢 LOGIN
    public User login(String username, String password) {
        String sql = "SELECT * FROM USERS WHERE Username = ? AND Password = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("Id")); // Changed from getString to getInt
                u.setUsername(rs.getString("Username"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getInt("Role"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🟢 CẬP NHẬT THÔNG TIN CÁ NHÂN
    public boolean updateProfile(int id, String fullname, String email, String password) { // Changed String id to int id
        String sql;
        if (password == null || password.isEmpty()) {
            sql = "UPDATE USERS SET Fullname=?, Email=? WHERE Id=?";
        } else {
            sql = "UPDATE USERS SET Fullname=?, Email=?, Password=? WHERE Id=?";
        }

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullname);
            ps.setString(2, email);
            if (password == null || password.isEmpty()) {
                ps.setInt(3, id); // Changed from setString to setInt
            } else {
                ps.setString(3, password);
                ps.setInt(4, id); // Changed from setString to setInt
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🟢 KIỂM TRA EMAIL ĐÃ TỒN TẠI
    public boolean checkUserExists(String email) {
        String sql = "SELECT * FROM USERS WHERE Email = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🟢 ĐĂNG KÝ NGƯỜI DÙNG MỚI
    public boolean register(User user) {
        String sql = "INSERT INTO USERS (Id, Username, Password, Fullname, Email, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getId()); // Changed from setString to setInt - ID should be an integer
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getEmail());
            ps.setInt(6, user.getRole());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🟢 GET USER BY ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM USERS WHERE Id = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("Id"));
                u.setUsername(rs.getString("Username"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getInt("Role"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🟢 GET NEXT AVAILABLE ID (for auto-increment simulation if needed)
    public int getNextUserId() {
        String sql = "SELECT MAX(Id) as maxId FROM USERS";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("maxId") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1; // Return 1 if no users exist
    }
}