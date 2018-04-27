import java.sql.*;

public class DatabaseManager {

    public static Connection makeConnection() {
        try {
            //This sign in is for Sadie
//                    "jdbc:mysql://localhost:5818/test?verifyServerCertificate=false&useSSL=true", "msandbox",
//                    "cs410*ss"

            //This sign in is for Steph
//                    "jdbc:mysql://localhost:5785/test?verifyServerCertificate=false&useSSL=true", "msandbox",
//                    "Sa03d48h!"

            Connection conn = null;
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:5785/test?verifyServerCertificate=false&useSSL=true", "msandbox",
                    "Sa03d48h!");
            // Do something with the Connection
            System.out.println("Database [test db] connection succeeded!");
            System.out.println();
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static void runQuery(Connection conn) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Persons");

            // Now do something with the ResultSet ....
            boolean rowsLeft = true;
            rs.first();
            while (rowsLeft) {
                System.out.println(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getString(3) + ":" + rs.getString(4)
                        + ":" + rs.getString(5));
                rowsLeft = rs.next();
            }
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
    }
}
