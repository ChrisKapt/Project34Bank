package net.sqlitetutorial;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author sqlitetutorial.net
 */
public class SelectApp {

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    
    /**
     * select all rows in the warehouses table
     */
    public String selectAll(String User){
        String sql = "SELECT Saldo, Naam, RekeningNummer FROM Users WHERE Naam = " + "\"" + User + "\"";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                return (rs.getDouble("Saldo") +  "\t" + 
                                   rs.getString("Naam") + "\t" +
                                   rs.getString("RekeningNummer"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    /**
     * 
     * @param User user id die bij deze user hoort, oftewel rfid
     * @return
     */
    public String selectPin(String User){
        String sql = ("SELECT Pin From Users WHERE User_id = " + "\"" + User + "\"");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                return rs.getString("Pin");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    /**
     * selecteert alle rfids(user_id) uit de database
     * @return
     */
    public String[] selectRFID(){
        String sql = ("SELECT User_id From Users");
        String[] rfids = new String[10];

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            int count=0;
            while (rs.next()) {
                rfids[count] = rs.getString("User_id");
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rfids;
    }
    
   
    /**
     * @param args the command line arguments
     *
    public static void main(String[] args) {
        SelectApp app = new SelectApp();
        //app.selectAll();
        app.selectPin("Henk");
    }
    */

}