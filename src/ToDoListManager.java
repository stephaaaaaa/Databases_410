/**
 * @author  Stephanie Labastida and Sadie Shirts
 */

import asg.cliche.*;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.text.ParseException;
import java.sql.*;

public class ToDoListManager {

    private boolean isSignedIn;
    private String usageMessage_NotSignedIn;
    private String usageMessage_SignedIn;
    private static TaskTracker taskTracker;
    private static String b_usr;
    private static String b_pswd;
    private static String s_usr;
    private static String s_pswd;
    private static int pNum;
    private static Connection conn;
    private static Session session;
    private static Statement stmt, stmt2;

    public ToDoListManager(){
        //isSignedIn = true; // set to true for testing purposes, and false for deployment
        usageMessage_NotSignedIn = "Type 'ssh'. Then, enter Bronco credentials, sandbox credentials, and your port number.\n" +
                "<Bronco User> <Bronco Password> <Sandbox User> <Sandbox Password> <Port Number>\n";
        usageMessage_SignedIn = "Usage:\n'active' \t\t\t\t\t\t\t\t\t= shows all active tasks\n'add' + "
                + "\"[label]\" \t\t\t\t\t\t\t= create a new task\n"
                + "'due' + [task_id] + \"[due_date]\" \t\t\t= assign due date to a task\n'tag' + [task_id] + \"[keywords]\" \t\t\t= assign keywords to a task\n"
                + "'finish' + [task_id] \t\t\t\t\t\t= mark a task as completed\n'cancel' + [task_id] \t\t\t\t\t\t= mark a task as inactive\n"
                + "'show' + [task_id] \t\t\t\t\t\t\t= show the details of the designated task\n'active' + \"[keyword]\" \t\t\t\t\t\t= show active tasks associated with the keyword\n"
                + "'completed' + \"[keyword]\" \t\t\t\t\t= show the completed tasks associated with the tag\n'overdue' \t\t\t\t\t\t\t\t\t= show all overdue tasks\n"
                + "'due today' \t\t\t\t\t\t\t\t= show all tasks due today\n'due soon' \t\t\t\t\t\t\t\t\t= show all tasks due in the next 3 days\n'rename' + [task_id] + \"[label]\" \t\t\t= rename the designated task\n"
                + "'search' + [token] \t\t\t\t\t\t\t= return all the tasks that contain the token in their label\n";
        //conn = makeConnection(); //moved to ssh so that we have credentials to sign in
        //taskTracker = new TaskTracker(); //need to pass conn into TaskTracker //moved to ssh for same reason
    }

    @Command(name = "ssh")
    public String enterCredentials(String bronco_user, String bronco_password,
                                 String sandbox_user, String sandbox_password, int portNum)
                    throws JSchException, ClassNotFoundException, SQLException{
        // assigning to local method variables to make sure that ssh + args is working
        isSignedIn = false;

        b_usr = bronco_user;
        b_pswd = bronco_password;
        s_usr = sandbox_user;
        s_pswd = sandbox_password;
        pNum = portNum;

        SSH_Manager ssh_man = new SSH_Manager(conn, session, stmt, stmt2);
        ssh_man.sshSignIn(b_usr, b_pswd, s_usr, s_pswd, pNum);
        System.out.println("passed the signIn function in ssh_manager");

        taskTracker = new TaskTracker(conn);
        return "Successful sign in!\n";
    }

    @Command(name = "help", abbrev = "-h")
    public String help(){
        if(isSignedIn == false)
            return usageMessage_NotSignedIn;
        else return usageMessage_SignedIn;
    }

    @Command(name = "exit")
    public String exit(){
        return "Exiting ToDoList_Manager ...";
    }

    @Command(name = "clear")
    public void clear(){

    }

    // helper method for all the task methods
    public boolean signedIn(){
        if(isSignedIn == true)
            return true;
        else return false;
    }

    @Command(name = "active")
    public String active(){
        if(signedIn() == true)
            taskTracker.showActiveTasks();
        return "No active tasks.";
    }

    // Works, except for the task ID
    @Command(name = "add")
    public void add(String newTaskLabel){
        if(signedIn() == true)
            taskTracker.addTask(newTaskLabel);
    }

    // Works
    @Command(name = "due")
    public void dueTasks(int taskNum, String dueDate) throws ParseException {
       if(signedIn() == true)
           taskTracker.setDueDate(taskNum, dueDate);
    }

    @Command(name = "tag")
    public void tagWithKeywords(int taskNum, String tags){
        if(signedIn() == true)
            taskTracker.addKeywords(taskNum, tags);
    }

    @Command(name = "finish")
    public String markTaskAsDone(int taskNum){
        if(signedIn() == true)
            taskTracker.markTaskComplete(taskNum);
        return "";
    }

    @Command(name = "cancel")
    public String cancelTask(int taskNum){
        if(signedIn() == true)
            taskTracker.cancelTask(taskNum);
        return "";
    }

    @Command(name = "show")
    public String showTaskDetails(int taskNum){
        if(signedIn() == true){
            taskTracker.showTask(taskNum);
        }
        return "";
    }

    @Command(name = "active")
    public String showActivePerTag(String tag){
        if(signedIn() == true)
            taskTracker.showActiveForTag(tag);
        return "";
    }

    @Command(name = "completed")
    public String showCompletedPerTag(String tag){
        if(signedIn() == true)
            taskTracker.showCompletedTasks();
        return "";
    }

    @Command(name = "overdue")
    public String showOverdue(){
        if(signedIn() == true)
            taskTracker.showOverdueTasks();
        return "";
    }

    @Command(name = "due today")
    public String showDueToday(){
        if(signedIn() == true)
            taskTracker.showOverdueTasks();
        return "";
    }

    @Command(name = "due soon")
    public String showDueSoon(){
        if(signedIn() == true)
            taskTracker.showDueSoon();
        return "";
    }

    @Command(name = "rename")
    public String rename(int taskNum, String newName){
        if(signedIn() == true)
            taskTracker.renameTask(taskNum, newName);
        return "";
    }

    @Command(name = "search")
    public String searchByKeyword(String keyword){
        if(signedIn() == true)
            taskTracker.searchByKeyword(keyword);
        return "";
    }

    /// BEGIN CONNECTION STUFF
    public static Connection makeConnection() {
        try {
            //Connection conn = null;
            conn = DriverManager.getConnection(
                    //uses variabes to make connection
                     "jdbc:mysql://localhost:" + pNum + "/test?verifyServerCertificate=false&useSSL=true", s_usr,
                     s_pswd

                    //This sign in is for Sadie
//                    "jdbc:mysql://localhost:5818/test?verifyServerCertificate=false&useSSL=true", "msandbox",
//                    "cs410*ss"

                    //This sign in is for Steph
//                    "jdbc:mysql://localhost:5785/test?verifyServerCertificate=false&useSSL=true", "msandbox",
//                    "Sa03d48h!"
            );
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


    /// END CONNECTION STUFF

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ClassNotFoundException, JSchException {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("JDBC driver loaded");
//            System.out.println();
//            System.out.println("Attempting to make connection ...");
//            Connection conn = makeConnection();
//            DatabaseManager.runQuery(connection);
//            System.out.println("Connection made!");

            ShellFactory.createConsoleShell("ToDoList_Manager", "Welcome to ToDoList_Manager!\nTo start, type" +
                            " 'ssh', followed by Bronco credentials , Sandbox credentials, and user port number.\n" +
                            "Type 'help' or '-h' for help.\n",
                    // Bronco credentials, sandbox credentials, and user port number.
                    new ToDoListManager()).commandLoop();

            //Connection conn = makeConnection();

            //runQuery(conn);
            conn.close();
            System.out.println();
            System.out.println("Database [test db] connection closed");
            System.out.println();
        } catch (Exception ex) {
            // handle the error
            System.err.println(ex);
        }


        //
//        Class.forName(""); // database driver class
//        Connection connection = DriverManager.getConnection("","",""); // connection url, username, password
//

    }
}
