import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SSH_Manager {

    public static Session doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
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
