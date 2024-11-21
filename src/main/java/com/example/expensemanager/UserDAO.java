package com.example.expensemanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/expense";
    private static final String USER = "root";
    private static final String PASS = "MySql13th13;";
    public void createUser(User user) {
        try{
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement ps = conn.prepareStatement("insert into user (name,mail,phone) values(?,?,?);");
        ps.setString(1,user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3,user.getPhone());
        ps.executeUpdate();
        ps.close();
        conn.close();}
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public User getUser(int id) {
        User user = new User();
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from user where id="+id);
            while(rs.next()){
                user.setId(id);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("mail"));
                user.setPhone(rs.getString("phone"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("mail");
                String phone = rs.getString("phone");
                User user = new User(name, email, phone);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order to avoid NullPointerException
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return userList;
    }
    public void updateUser(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE user SET name = ?, mail = ?, phone = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order to avoid NullPointerException
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public void deleteUser(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order to avoid NullPointerException
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


}
