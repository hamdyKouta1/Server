/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 *
 * @author elrayes
 */
public class JDBC {
    
    
    final private String url = "jdbc:postgresql://localhost:5432/userdata";
    final private String user = "postgres";
    final private String password = "123";
        public static Timestamp time;


    
    public void saveChatMsg(String getFrom, String getTo, String getMsg) throws ClassNotFoundException {
        try (
                Connection connection = DriverManager.getConnection(url, user, password)) {

            String from = getFrom;
            String to = getTo;
            String msg = getMsg;
            time = new Timestamp(System.currentTimeMillis());

            String insertSql = "INSERT INTO msg_data (fromport, toport , msg, attime) VALUES (?, ?, ?, ?)";
        Class.forName("org.postgresql.Driver");

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setString(1, from);
                preparedStatement.setString(2, to);
                preparedStatement.setString(3, msg);
                preparedStatement.setTimestamp(4, time);

                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
