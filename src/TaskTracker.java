import java.text.DateFormat;
import java.text.ParseException;
import java.util.LinkedList;
import asg.cliche.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * An all purpose class that will create a list to keep track of
 * task objects, which will be part of the backend for the to-do list
 * manager.
 *
 * @author Stephanie Labastida
 */
public class TaskTracker {
    private LinkedList<Task> taskList;
    private Date today;
    private String activeTasks;


    public TaskTracker(){
        taskList = new LinkedList<>();
        today = new Date(); // gets the current date and time
        activeTasks = "------------------------- ACTIVE TASKS -------------------------\n";

    }

    public Task addTask(String taskName){
        Task newTask = new Task(taskName);
        //newTask.setTaskID(currentTaskID);
        taskList.add(newTask);
        return newTask;
    }

    // Setting return value to String so an error message can be returned
    public String setDueDate(int taskNum, String dueDate) throws ParseException{
        Date date_due = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
        for(Task task:taskList){
            if(task.getTaskID() == taskNum)
                task.setDueDate(date_due);
            else return "No tasks in the list correspond to ID value" + taskNum;
        }
        return "";
    }

    public void removeTask(String taskName){
        for(Task task : taskList){
            if(task.getTaskLabel().equals(taskName)){
                taskList.remove();
            }
        }
    }

    public String showActiveTasks(){
        for(Task task:taskList){
            if(task.getActiveStatus() == true)
                activeTasks += task.showDetails();
        }
        return activeTasks;
    }

    public String addKeywords(int taskNum, String tags){
        for(Task task:taskList){
            if(task.getTaskID() == taskNum)
                    task.addKeyword(tags);
            else
                return "No tasks in the list correspond to ID value" + taskNum + ".";
        }
        return "";
    }

    public String markTaskComplete(int taskNum) {
        for (Task task : taskList) {
            if (task.getTaskID() == taskNum) {
                if (task.getCompletionStatus() == false)
                    task.finishTask();
                else
                    return "Task " + taskNum + " is already complete";
            }else
                return "No tasks in the list correspond to ID value" + taskNum + ".";
        }
        return "";
    }

    public String cancelTask(int taskNum){
        for(Task task:taskList){
            if(task.getTaskID() == taskNum){
                task.cancelTask();
            } else return "No tasks in the list correspond to ID value" + taskNum + ".";
        }
        return "";
    }

    public String showTask(int taskNum){
        for(Task task:taskList){
            if(task.getTaskID() == taskNum){
                task.showDetails();
            }else return "No tasks in the list correspond to ID value" + taskNum + ".";
        }
        return "";
    }

    public String showActiveForTag(String tag){
        LinkedList<Task> activeTasksAssociated_Tag = new LinkedList<>();
        for(Task task:taskList){
            if(task.getKeywords().contains(tag))
                activeTasksAssociated_Tag.add(task);
            else return "No tasks in the list have '" + tag + "' tag associated with them.";
        }
        return activeTasksAssociated_Tag.toString();
    }

    public String showCompletedTasks(){
        String completedTasks = "";
        for(Task task: taskList){
            if(task.getCompletionStatus() == true){
                completedTasks.concat(task.getTaskLabel());
            }
        }
        return completedTasks;
    }

    public String showOverdueTasks(){
        String overdueTasks = "";
        for(Task task:taskList){
            if(task.getDueDate().before(today))
                overdueTasks += task.showDetails();
        }
        if(overdueTasks.equals(""))
            return "No overdue tasks.";
        return "";
    }

    public String showDueSoon(){
        String dueSoon = "";
        Date tomorrow = new Date(System.currentTimeMillis() + 86400000);
        Date inTwoDays = new Date(System.currentTimeMillis() + 86400000 * 2);
        Date inThreeDays = new Date(System.currentTimeMillis() + 86400000 * 3);
        for(Task task:taskList){
            Date dueDate = task.getDueDate();
            if(dueDate.equals(today) || dueDate.equals(tomorrow) ||
                    dueDate.equals(inTwoDays) || dueDate.equals(inThreeDays)){
                dueSoon += task.showDetails();
            }
        }
        if(dueSoon.equals(""))
            return "No tasks are due within the next three days.";
        return dueSoon;
    }

    public String renameTask(int taskNum, String newLabel){
        for(Task task:taskList){
            if(task.getTaskID() == taskNum){
                task.setTaskLabel(newLabel);
            } else return "No tasks in the list correspond to ID value" + taskNum + ".";
        }
        return "";
    }

    public String searchByKeyword(String keyword){
        String tasksWithKeyword = "";
        for(Task task:taskList){
            if(task.getKeywords().contains(keyword))
                tasksWithKeyword += task.showDetails();
        }
        if(tasksWithKeyword.equals(""))
            return "No tasks in the list contain the keyword: " + keyword + ".";
        return tasksWithKeyword;
    }


}
