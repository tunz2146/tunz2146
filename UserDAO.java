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
                u.setId(rs.getString("Id"));  // This should work now since setId expects String
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
    public boolean updateProfile(String id, String fullname, String email, String password) {
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
                ps.setString(3, id);
            } else {
                ps.setString(3, password);
                ps.setString(4, id);
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

            ps.setString(1, user.getId());        // This should work now since getId returns String
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
}