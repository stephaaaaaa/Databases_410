/**
 * @author  Stephanie Labastida
 */

import asg.cliche.*;

import java.io.IOException;
import java.util.List;

public class ToDoListManager {

    private boolean isSignedIn;
    private String usageMessage_NotSignedIn;
    private String usageMessage_SignedIn;
    ConsoleIO io;

    public ToDoListManager(){
        isSignedIn = false;
        usageMessage_NotSignedIn = "Type 'sign in' or '-s'. Then, enter Bronco credentials, sandbox credentials, and your port number.\n" +
                "<Bronco User> <Bronco Password> <Sandbox User> <Sandbox Password> <Port Number>";
        usageMessage_SignedIn = "Usage:\n'active' \t\t\t\t\t\t= shows all active tasks\n'add' + [label] \t\t\t\t= create a new task\n" +
                "'due' + [task_id] + [due_date] \t= assign due date to a task\n'tag' + [task_id] + [keywords] \t= assign keywords to a task\n" +
                "'finish' + [task_id] \t\t\t= mark a task as completed\n'cancel' + [task_id] \t\t\t= mark a task as inactive\n" +
                "'show' + [task_id] \t\t\t\t= show the details of the designated task\n'active' + [keyword] \t\t\t= show active tasks associated with the keyword\n" +
                "'completed' + [keyword] \t\t= show the completed tasks associated with the tag\n'overdue' \t\t\t\t\t\t= show all overdue tasks\n" +
                "'due today' \t\t\t\t\t= show all tasks due today\n'due soon' \t\t\t\t\t\t= show all tasks due in the next 3 days\n'rename' + [task_id] + [label] \t= rename the designated task\n" +
                "'search' + [token] \t\t\t\t= return all the tasks that contain the token in their label";
        io = new ConsoleIO();
    }

    @Command(name = "ssh")
    public void enterCredentials(String bronco_user, String bronco_password,
                                 String sandbox_user, String sandbox_password, int portNum){
        (io).cliEnterLoop(); // I believe what this does is that it will take the arguments and do stuff with them
    }

    @Command(name = "exit")
    public String exit(){
        return "Exiting ToDoList_Manager ...";
    }

    @Command(name = "help", abbrev = "-h")
    public String help(){
        if(isSignedIn == false)
            return usageMessage_NotSignedIn;
        else return usageMessage_SignedIn;
    }

    @Command(name = "clear")
    public void clear(){

    }


    public static void main(String[] args) throws IOException{
        TaskGenerator generator = new TaskGenerator();
        ShellFactory.createConsoleShell("ToDoList_Manager", "Welcome to ToDoList_Manager!\nTo start, type" +
                        " 'ssh', followed by Bronco credentials, Sandbox credentials, and user port number.\n" +
                        "Type 'help' or '-h' for help.\n",
                // Bronco credentials, sandbox credentials, and user port number.
                new ToDoListManager()).commandLoop();


    }
}
