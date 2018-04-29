import java.sql.*;

/**
 * Contains all the queries that are being called from
 * Task Tracker
 */

public class Queries {

    private static void sqlExceptionCatch(SQLException ex){
        System.err.println("SQLException: " + ex.getMessage());
        System.err.println("SQLState: " + ex.getSQLState());
        System.err.println("VendorError: " + ex.getErrorCode());
    }

    private static void finallyBehavior(ResultSet rs, Statement stmt){
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

    public static void run_DisplayQuery(String query, Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean rowsLeft = true;
            rs.first();
            //if(rs.next()) {
                while (rowsLeft) {
                    //if(rs.next()) {
                    System.out.println(
                            "Task " + rs.getInt("task_id") + ":\n\"" +
                                    rs.getString("task_label") +
                                    "\"\nCreation Date: " + rs.getString("time_stamp") +
                                    "\nDue Date: " +
                                    rs.getString("due_date") + "\n" +
                                    "Tags: " + rs.getString("tag") + "\n" /*+
                        "Completed: " + rs.getString("is_complete") + "\n" +
                        "Canceled: " + rs.getString("is_cancelled") + "\n"*/);
                    //}
                    rowsLeft = rs.next();
                }
            //}
        } catch (SQLException ex) {
            sqlExceptionCatch(ex);
        } finally {
            finallyBehavior(rs, stmt);
        }
    }

    public static void run_UpdateQuery(String query, Connection conn) {
        PreparedStatement stmt = null;
        int result = 0;

        try {
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate();

//            boolean rowsLeft = true;
//            result.first();
//            while (rowsLeft) {
//                System.out.println("Task "+ result.getInt(1) + ":\n\"" + result.getString(2) + "\"\nCreation Date: " +
//                        result.getString(3) + "\nDue Date: " + result.getString(4) + "\n");
//                rowsLeft = result.next();
//            }
        } catch (SQLException ex) {
            sqlExceptionCatch(ex);
        } finally {
            //finallyBehavior(result, stmt);
        }
    }
}
