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

    public static void run_ActiveQuery(String query, Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean rowsLeft = true;
            rs.first();
            while (rowsLeft) {
                System.out.println("Task "+ rs.getInt(1) + ":\n\"" + rs.getString(2) + "\"\nCreation Date: " +
                        rs.getString(3) + "\nDue Date: " + rs.getString(4) + "\n");
                rowsLeft = rs.next();
            }
        } catch (SQLException ex) {
            sqlExceptionCatch(ex);
        } finally {
            finallyBehavior(rs, stmt);
        }
    }

    public static void run_AddQuery(String query, Connection conn){
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

    public static void run_SetDueDateQuery(String query) {
    }

    public static void run_RemoveTaskQuery(String query) {
    }

    public static void run_AddKeywordQuery(String query) {
    }

    public static void run_TaskCompleteQuery(String query) {
    }

    public static void run_CancelTaskQuery(String query) {
    }

    public static void run_ShowTaskQuery(String query) {
    }

    public static void run_ShowActiveTagQuery(String query) {
    }

    public static void run_CompletedTasksQuery(String query) {
    }

    public static void run_OverdueTasksQuery(String query) {
    }

    public static void run_DueTodayQuery(String query) {
    }

    public static void run_DueSoonQuery(String query) {
    }

    public static void run_RenameQuery(String query) {
    }

    public static void run_SearchKeywordQuery(String query) {
    }
}
