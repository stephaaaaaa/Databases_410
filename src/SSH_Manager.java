import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;

public class SSH_Manager {

    private static Connection conn;
    private static Session sess;
    private static Statement stmt, stmt2;

    public SSH_Manager(Connection conn, Session session, Statement stmt, Statement stmt2){
        this.conn = conn;
        this.sess = session;
        this.stmt = stmt;
        this.stmt2 = stmt2;
    }

    public static String sshSignIn(String bronco_user, String bronco_password, String sandbox_user,
                                 String sandbox_password, int user_PortNum)
    throws  SQLException, ClassNotFoundException, JSchException{

            try
            {
                String strSshUser = bronco_user;                  // SSH loging username
                String strSshPassword = bronco_password;                   // SSH login password
                String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
                int nSshPort = 22;                                    // remote SSH host port number
                String strRemoteHost = "localhost";  // hostname or ip of your database server
                int nLocalPort = 64736;  // local port number use to bind SSH tunnel // Supposed to be any local one on your machine

                String strDbUser = sandbox_user;                    // database loging username
                String strDbPassword = sandbox_password;                    // database login password
                int nRemotePort = user_PortNum; // remote port number of your database

                // SSH Tunnel
                sess = doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);


                // Load database driver and get connection
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);


                // Executing transactions
                conn.setAutoCommit(false);//transaction block starts
                stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery("select * from `Company`.`employee`");

                // Insert into tables
                String[] data = {"boise", "nampa"};
                stmt2 = insertLocations(conn,data);
               conn.commit(); //transaction block ends

                System.out.println("Transaction done!");

               // Use tables to navigate through results
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
                conn.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction
            }

            return "Sign in successful.";

    }

    public static Session doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
    {
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

    public static Statement insertLocations(Connection con, String[] data) throws SQLException {
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
