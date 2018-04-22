/**
 * @author  Stephanie Labastida
 */

import asg.cliche.*;
import java.io.IOException;

public class ToDoListManager {

    public ToDoListManager(){

    }

    static String usageMessage = "Usage:\n\n'active' \t\t\t\t\t\t= shows all active tasks\n'add' + [label] \t\t\t\t= create a new task\n" +
            "'due' + [task_id] + [due_date] \t= assign due date to a task\n'tag' + [task_id] + [keywords] \t= assign keywords to a task\n" +
            "'finish' + [task_id] \t\t\t= mark a task as completed\n'cancel' + [task_id] \t\t\t= mark a task as inactive\n" +
            "'show' + [task_id] \t\t\t\t= show the details of the designated task\n'active' + [keyword] \t\t\t= show active tasks associated with the keyword\n" +
            "'completed' + [keyword] \t\t= show the completed tasks associated with the tag\n'overdue' \t\t\t\t\t\t= show all overdue tasks\n" +
            "'due today' \t\t\t\t\t= show all tasks due today\n'due soon' \t\t\t\t\t\t= show all tasks due in the next 3 days\n'rename' + [task_id] + [label] \t= rename the designated task\n" +
            "'search' + [token] \t\t\t\t= return all the tasks that contain the token in their label";

    public static void main(String[] args) throws IOException{

        //System.out.println("Welcome. Please follow usage:\n<BroncoUserid> <BroncoPassword> <sandboxUSerID> <sandbox password>" +
          //      " <yourportnumber>");
        ShellFactory.createConsoleShell("toDo_Master","","").commandLoop();
//        if(args.length == 0){
//            System.out.println("Too few arguments. " + usageMessage);
//        }
    }
}
