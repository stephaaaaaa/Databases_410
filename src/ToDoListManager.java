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
    private static int db_portNum;
    private static Connection conn;
    private static Session session;
    private static Statement stmt, stmt2;

    public ToDoListManager(){
        isSignedIn = false;
        //isSignedIn = true; // set to true for testing purposes, and false for deployment
        usageMessage_NotSignedIn = "Type 'ssh'. Then, enter Bronco credentials, sandbox credentials, and your database's port number.\n" +
                "<Bronco User> <Bronco Password> <Sandbox User> <Sandbox Password> <Port Number>\n";
        usageMessage_SignedIn = "Usage:\n'active' \t\t\t\t\t\t\t\t\t= shows all active tasks\n'add' + "
                + "\"[label]\" \t\t\t\t\t\t\t= create a new task\n"
                + "'due' + [task_id] + \"[due_date]\" \t\t\t= assign due date to a task\n'tag' + [task_id] + \"[keywords]\" \t\t\t= assign keywords to a task\n"
                + "'finish' + [task_id] \t\t\t\t\t\t= mark a task as completed\n'cancel' + [task_id] \t\t\t\t\t\t= mark a task as inactive\n"
                + "'show' + [task_id] \t\t\t\t\t\t\t= show the details of the designated task\n'active' + \"[keyword]\" \t\t\t\t\t\t= show active tasks associated with the keyword\n"
                + "'completed' + \"[keyword]\" \t\t\t\t\t= show the completed tasks associated with the tag\n'overdue' \t\t\t\t\t\t\t\t\t= show all overdue tasks\n"
                + "'dueToday' \t\t\t\t\t\t\t\t\t= show all tasks due today\n'dueSoon' \t\t\t\t\t\t\t\t\t= show all tasks due in the next 3 days\n'rename' + [task_id] + \"[label]\" \t\t\t= rename the designated task\n"
                + "'search' + [token] \t\t\t\t\t\t\t= return all the tasks that contain the token in their label\n";
    }

    private Connection loadDB(int pNum, String sb_usr, String sb_pswd) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost:64736" + "/todo"
                + "?verifyServerCertificate=false&useSSL=true";
        Connection newConn = DriverManager.getConnection(connectionURL, sb_usr, sb_pswd);
        return newConn;
    }

    @Command(name = "ssh")
    public String enterCredentials(String bronco_user, String bronco_password,
                                 String sandbox_user, String sandbox_password, int portNum)
                    throws JSchException, ClassNotFoundException, SQLException{

        b_usr = bronco_user;
        b_pswd = bronco_password;
        s_usr = sandbox_user;
        s_pswd = sandbox_password;
        db_portNum = portNum;

        //SSH_Manager ssh_man = new SSH_Manager(conn, session, stmt, stmt2);
        session = SSH_Manager.sshSignIn(b_usr, b_pswd, s_usr, s_pswd, db_portNum);
        System.out.println("Successfully established SSH Connection.");
        conn = loadDB(db_portNum, s_usr, s_pswd);
        System.out.println("Successfully established DB connection.");

        taskTracker = new TaskTracker(conn);
        isSignedIn = true;
        return "Sign in successful!\n";
    }

    @Command(name = "help", abbrev = "-h")
    public String help(){
        if(isSignedIn == false)
            return usageMessage_NotSignedIn;
        else return usageMessage_SignedIn;
    }

    @Command(name = "exit")
    public String exit() throws SQLException{
        if(stmt!=null)
            stmt.close();

        if(stmt2!=null)
            stmt.close();

        if(conn != null)
            conn.close();

        if(session != null)
            session.disconnect();

        return "Exiting Task Tracker ...";
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
        else return "Not signed in to Task Tracker.";
        return "";
    }

    // Works, except for the task ID
    @Command(name = "add")
    public String add(String newTaskLabel){
        if(signedIn() == true)
            taskTracker.addTask(newTaskLabel);
        else return "Not signed in to Task Tracker.";
        return "";
    }

    /**
     * FOR TESTING PURPOSES ONLY
     * REMOVE FOR SUBMISSION
     *
     * @param taskNum
     * @return
     */
    @Command(name = "delete")
    public String delete(int taskNum){
        if(signedIn() == true)
            taskTracker.removeTask(taskNum);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "due")
    public String dueTasks(int taskNum, String dueDate) throws ParseException {
       if(signedIn() == true)
           taskTracker.setDueDate(taskNum, dueDate);
       else return "Not signed in to Task Tracker";
       return "";
    }

    @Command(name = "tag")
    public String tagWithKeywords(int taskNum, String tags){
        if(signedIn() == true)
            taskTracker.addKeywords(taskNum, tags);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "finish")
    public String markTaskAsDone(int taskNum){
        if(signedIn() == true)
            taskTracker.markTaskComplete(taskNum);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "cancel")
    public String cancelTask(int taskNum){
        if(signedIn() == true)
            taskTracker.cancelTask(taskNum);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "show")
    public String showTaskDetails(int taskNum){
        if(signedIn() == true)
            taskTracker.showTask(taskNum);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "active")
    public String showActivePerTag(String tag){
        if(signedIn() == true)
            taskTracker.showActiveForTag(tag);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "completed")
    public String showCompletedPerTag(String tag){
        if(signedIn() == true)
            taskTracker.showCompletedTasks_ByTag(tag);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "overdue")
    public String showOverdue(){
        if(signedIn() == true)
            taskTracker.showOverdueTasks();
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "dueToday")
    public String showDueToday(){
        if(signedIn() == true)
            taskTracker.showDueToday();
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "dueSoon")
    public String showDueSoon(){
        if(signedIn() == true)
            taskTracker.showDueSoon();
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "rename")
    public String rename(int taskNum, String newName){
        if(signedIn() == true)
            taskTracker.renameTask(taskNum, newName);
        else return "Not signed in to Task Tracker";
        return "";
    }

    @Command(name = "search")
    public String searchByKeyword(String keyword){
        if(signedIn() == true)
            taskTracker.searchByKeyword(keyword);
        else return "Not signed in to Task Tracker";
        return "";
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ClassNotFoundException, JSchException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            ShellFactory.createConsoleShell("ToDoList_Manager", "Welcome to Task Tracker!\nTo start, type" +
                            " 'ssh', followed by Bronco credentials , Sandbox credentials, and user port number.\n" +
                            "Type 'help' or '-h' for help.\n",
                    new ToDoListManager()).commandLoop();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
