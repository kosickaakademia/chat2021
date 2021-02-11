package sk.kosickaakademia.onofrej.chat.database;

import sk.kosickaakademia.onofrej.chat.util.Util;
import sk.kosickaakademia.onofrej.entity.Message;
import sk.kosickaakademia.onofrej.entity.User;

import java.sql.*;
import java.util.List;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";
    private final String insertNewUser = "INSERT INTO user (login, password) VALUES (?,?)";
    private final String loginUser = "Select * FROM user Where login LIKE ? and password LIKE ?";
    private final String newMessage = "INSERT INTO message( frto, to, text) VALUES (?,?,?)";

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public void test() {
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Problem !!!!!!!!!!!");
            } else {
                System.out.println("Success !");
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean insertNewUser(String login, String password) {
        if (login == null || login.equals("") || password == null || password.length() < 6)
            return false;
        String hashPassword = new Util().getMd5(password);
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Connection error !");
                return false;
            }
            PreparedStatement ps = con.prepareStatement(insertNewUser);
            ps.setString(1, login);
            ps.setString(2, hashPassword);
            int result = ps.executeUpdate();
            con.close();
            if (result == 0)
                return false;
            else {
                System.out.println("User " + login + " has been added to the database !");
                return true;
            }
        } catch (Exception ex) {
            System.out.println("User already exists!");
        }
        return true;
    }

    public User loginUser(String login, String password) {
        if (login == null || login.equals("") || password == null || password.length() < 6)
            return null;

        String hashPassword = new Util().getMd5(password);
        try {
            Connection con = getConnection();    //1
            if (con == null) {
                System.out.println("Connection error!");
                return null;
            }
            PreparedStatement ps = con.prepareStatement(loginUser);                                                         ;
            ps.setString(1, login);
            ps.setString(2, hashPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Success login!");
                int id = rs.getInt("id");
                User user = new User(id, login, hashPassword);
                con.close();
                return user;
            } else {
                con.close();
                System.out.println("Incorect credentials!");
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean changePassword(String login , String oldPassword, String newPassword){
        // TO DO
        return false;
    }

    public boolean sendMessage(int from, String toUser, String text){
        if(text==null || text.equals(""))
            return false;
        int to = getUserId(toUser);
        if(to==-1)
            return false;
        try {
            Connection con = getConnection();
            if(con ==null){
                System.out.println("Connection issue!");
                return false;
            }
            PreparedStatement ps = con.prepareStatement(newMessage);
            ps.setInt(1,from);
            ps.setInt(2, to);
            ps.setString(3, text);
            int result = ps.executeUpdate();
            con.close();
            if(result<1){
                System.out.println("Message not delivered!");
                return false;
            }
            else{
                System.out.println("Message sent successfully!");
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public int getUserId(String login){
        
        return -1;
    }

    public List<Message> getMyMessages(String login){

        // volame metodu deleteAllMyMessages
        return null;
    }

    public void deleteAllMyMessages(String login){

    }



}
