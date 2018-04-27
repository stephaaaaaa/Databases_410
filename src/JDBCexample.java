import java.io.*;
import java.sql.*;
import java.util.*;

import com.jcraft.jsch.*;

public class JDBCexample {

    public static void main(String[] args) throws ClassNotFoundException, JSchException, SQLException {
        if (args.length<5){
            System.out.println("Usage DBConnectTest <BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password> <yourportnumber>");
        }
        else{
            Connection con = null;
            Session session = null;
            Statement stmt = null, stmt2 = null;
            try
            {
                String strSshUser = args[0];                  // SSH loging username
                String strSshPassword = args[1];                   // SSH login password
                String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
                int nSshPort = 22;                                    // remote SSH host port number
                String strRemoteHost = "localhost";  // hostname or ip of your database server
                int nLocalPort = 3367;  // local port number use to bind SSH tunnel

                String strDbUser = args[2];                    // database loging username
                String strDbPassword = args[3];                    // database login password
                int nRemotePort = Integer.parseInt(args[4]); // remote port number of your database

                /*
                 * STEP 0
                 * CREATE a SSH session to ONYX
                 *
                 * */
                session = JDBCexample.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);


                /*
                 * STEP 1 and 2
                 * LOAD the Database DRIVER and obtain a CONNECTION
                 *
                 * */
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);

                /*
                 * STEP 3
                 * EXECUTE STATEMENTS (by using Transactions)
                 *
                 * */

                con.setAutoCommit(false);//transaction block starts


                stmt = con.createStatement();

                ResultSet resultSet = stmt.executeQuery("select * from `Company`.`employee`");

                /*TO INSERT INTO TABLES
                 * You can also read from a file and store in a data structure of your choice*/
                String[] data = {"boise", "nampa"};
                stmt2 = insertLocations(con,data);

                con.commit(); //transaction block ends

                System.out.println("Transaction done!");

                /*
                 * STEP 4
                 * Use result sets (tables) to navigate through the results
                 *
                 * */

                ResultSetMetaData rsmd = resultSet.getMetaData();

                int columnsNumber = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println(" ");
                }



            }
            catch( SQLException e )
            {
                System.out.println(e.getMessage());
                con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction
            }
            finally{

                /*
                 * STEP 5
                 * CLOSE CONNECTION AND SSH SESSION
                 *
                 * */

                if(stmt!=null)
                    stmt.close();

                if(stmt2!=null)
                    stmt.close();

                con.setAutoCommit(true); // restore dafault mode
                con.close();
                session.disconnect();
            }

        }
    }

    private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
    {
        /*This is one of the available choices to connect to mysql
         * If you think you know another way, you can go ahead*/

        final JSch jsch = new JSch();
        java.util.Properties configuration = new java.util.Properties();
        configuration.put("StrictHostKeyChecking", "no");

        Session session = jsch.getSession( strSshUser, strSshHost, 22 );
        session.setPassword( strSshPassword );

        session.setConfig(configuration);
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
        return session;
    }


    private static Statement insertLocations(Connection con, String[] data) throws SQLException {
        String sql;
        java.sql.Statement stmt = null;
        stmt = con.createStatement();
        for(int i=0;i<data.length;i++){
            sql = "INSERT INTO `Company`.`dept_locations`(`dnumber`,`dlocation`)VALUES(1,'"+data[i]+"')";
            int res = stmt.executeUpdate(sql);
            System.out.println(res);
        }
        return stmt;
    }

}
