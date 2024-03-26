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
    
    final private String url = "jdbc:postgresql://localhost:5432/UserData";
    final private String user = "postgres";
    final private String password = "123";
    
    public void saveChatMsg(String getFrom, String getTo, String getMsg, Timestamp getTime) {
        try (
                Connection connection = DriverManager.getConnection(url, user, password)) {

            String from = getFrom;
            String to = getTo;
            String msg = getMsg;
            Timestamp timestamp = getTime;

            String insertSql = "INSERT INTO msg_data (fromport, toport , msg, attime) VALUES (?, ?, ?, ?)";

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

                preparedStatement.setString(1, from);
                preparedStatement.setString(2, to);
                preparedStatement.setString(3, msg);
                preparedStatement.setTimestamp(4, timestamp);

                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " row(s) inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
