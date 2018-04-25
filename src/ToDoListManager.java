/**
 * @author  Stephanie Labastida
 */

import asg.cliche.*;
import java.io.IOException;
import java.text.ParseException;
import java.sql.*;

public class ToDoListManager {

    private boolean isSignedIn;
    private String usageMessage_NotSignedIn;
    private String usageMessage_SignedIn;
    private static TaskTracker taskTracker;

    public ToDoListManager(){
        isSignedIn = false;
        usageMessage_NotSignedIn = "Type 'ssh'. Then, enter Bronco credentials, sandbox credentials, and your port number.\n" +
                "<Bronco User> <Bronco Password> <Sandbox User> <Sandbox Password> <Port Number>\n";
        usageMessage_SignedIn = "Usage:\n'active' \t\t\t\t\t\t= shows all active tasks\n'add' + [label] \t\t\t\t= create a new task\n" +
                "'due' + [task_id] + [due_date] \t= assign due date to a task\n'tag' + [task_id] + [keywords] \t= assign keywords to a task\n" +
                "'finish' + [task_id] \t\t\t= mark a task as completed\n'cancel' + [task_id] \t\t\t= mark a task as inactive\n" +
                "'show' + [task_id] \t\t\t\t= show the details of the designated task\n'active' + [keyword] \t\t\t= show active tasks associated with the keyword\n" +
                "'completed' + [keyword] \t\t= show the completed tasks associated with the tag\n'overdue' \t\t\t\t\t\t= show all overdue tasks\n" +
                "'due today' \t\t\t\t\t= show all tasks due today\n'due soon' \t\t\t\t\t\t= show all tasks due in the next 3 days\n'rename' + [task_id] + [label] \t= rename the designated task\n" +
                "'search' + [token] \t\t\t\t= return all the tasks that contain the token in their label\n";
        taskTracker = new TaskTracker();
    }

    @Command(name = "ssh")
    public String enterCredentials(String bronco_user, String bronco_password,
                                 String sandbox_user, String sandbox_password, int portNum){
        // assigning to local method variables to make sure that ssh + args is working
        String b_usr = bronco_user;
        String b_pswd = bronco_password;
        String s_usr = sandbox_user;
        String s_pswd = sandbox_password;
        int pNum = portNum;


        isSignedIn = true;
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
            return taskTracker.showActiveTasks();
        return "No active tasks.";
    }

    @Command(name = "add")
    public void add(String newTaskLabel){
        if(signedIn() == true)
            taskTracker.addTask(newTaskLabel);
    }

    @Command(name = "due")
    public void dueTasks(int taskNum, String dueDate) throws ParseException {
       if(signedIn() == true)
           taskTracker.setDueDate(taskNum, dueDate);
    }

    @Command(name = "tag")
    public void tagWithKeywords(int taskNum, String[] tags){
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
            return taskTracker.showTask(taskNum);
        }
        return "";
    }

    @Command(name = "active")
    public String showActivePerTag(String tag){
        if(signedIn() == true)
            return taskTracker.showActiveForTag(tag);
        return "";
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
//
//        Class.forName(""); // database driver class
//        Connection connection = DriverManager.getConnection("","",""); // connection url, username, password
//

        ShellFactory.createConsoleShell("ToDoList_Manager", "Welcome to ToDoList_Manager!\nTo start, type" +
                        " 'ssh', followed by Bronco credentials, Sandbox credentials, and user port number.\n" +
                        "Type 'help' or '-h' for help.\n",
                // Bronco credentials, sandbox credentials, and user port number.
                new ToDoListManager()).commandLoop();


    }
}
