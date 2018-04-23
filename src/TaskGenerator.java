import java.util.LinkedList;
import asg.cliche.*;

/**
 * An all purpose class that will create a list to keep track of
 * task objects, which will be part of the backend for the to-do list
 * manager.
 *
 * @author Stephanie Labastida
 */
public class TaskGenerator {
    private LinkedList<Task> taskList;

    public TaskGenerator(){
        taskList = new LinkedList<>();
    }

    public Task addTask(String taskName){
        Task newTask = new Task(taskName);
        taskList.add(newTask);
        return newTask;
    }

    public void removeTask(String taskName){
        for(Task task : taskList){
            if(task.getTaskLabel().equals(taskName)){
                taskList.remove();
            }
        }
    }
}
