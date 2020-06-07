package Database;
import java.sql.*;


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
        String url = "jdbc:sqlite:C://Users//ricke//Downloads//TI//Y1//Project 34//Testing//src//Database//test.db";
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
    public String selectPin(String rekeningNummer){
        String sql = ("SELECT Pin From Users WHERE RekeningNummer = " + "\"" + rekeningNummer + "\"");

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

    public double selectSaldo(String rekeningNummer){
        String sql = ("SELECT Saldo From Users WHERE RekeningNummer = " + "\"" + rekeningNummer + "\"");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return rs.getDouble("Saldo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


    public String selectRekeningNummer(String RFID){
        String sql = ("SELECT RekeningNummer From Users WHERE RFID = " + "\"" + RFID + "\"");
        String rfids;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop throught set
            while (rs.next()) {
                return rs.getString("RekeningNummer");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public int selectPogingen(String rekeningNummer){
        String sql = ("SELECT Pogingen From Users WHERE RekeningNummer = " + "\"" + rekeningNummer + "\"");

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop throught set
            while (rs.next()) {
                return rs.getInt("Pogingen");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 10;
    }

    public boolean checkRFID(String RFID){
        String sql = ("SELECT RFID From Users WHERE RFID = " + "\"" + RFID + "\"");
        String rfid = "";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop throught set
            while (rs.next()) {
                rfid = rs.getString("RFID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(rfid.equals("")){
            return false;
        } else {
            return true;
        }
    }


    public void updatePogingen(String rekeningNummer) {
        String sql = "UPDATE Users SET Pogingen = Pogingen + 1 " + "WHERE RekeningNummer = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, rekeningNummer);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetPogingen(String rekeningNummer) {
        String sql = "UPDATE Users SET Pogingen = 0 " + "WHERE RekeningNummer = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, rekeningNummer);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSaldo(String rekeningNummer, double pinBedrag) {
        String sql = "UPDATE Users SET Saldo = ? " + "WHERE RekeningNummer = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            double saldo = this.selectSaldo(rekeningNummer);
            double nieuwSaldo = saldo-pinBedrag;
            // set the corresponding param
            pstmt.setDouble(1, nieuwSaldo);
            pstmt.setString(2, rekeningNummer);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransactie(String user, double bedrag,String datum, int id){
        String sql = "INSERT INTO Transacties(TransactieId,Bedrag,Datum,RekeningNummer) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            pstmt.setDouble(2, bedrag);
            pstmt.setString(3, datum);
            pstmt.setString(4,user);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getNextId(){
        String sql = ("SELECT COUNT(*) FROM Transacties");
        int count=0;
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop throught set
            while (rs.next()) {
                count = rs.getInt(1)+1;
                System.out.println("id van volgende trans is "+ count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
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