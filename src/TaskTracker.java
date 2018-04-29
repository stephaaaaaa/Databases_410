import java.text.ParseException;
import java.util.LinkedList;
import java.util.Date;
import java.sql.*;

/**
 * An all purpose class that will create a list to keep track of
 * task objects, which will be part of the backend for the to-do list
 * manager.
 *
 * @author Stephanie Labastida
 */
public class TaskTracker {
    private Date today;
    private String activeTasks;
    private static Connection conn;


    public TaskTracker(Connection conn){
    	this.conn = conn;
        today = new Date(); // gets the current date and time
        activeTasks = "------------------------- ACTIVE TASKS -------------------------\n";

    }

	public void showActiveTasks(){
		// for(Task task:taskList){
		//if(task.getActiveStatus() == true) {
		String query =
				"SELECT task.task_id, task.task_label, task.time_stamp, task.due_date, task.tag, task.is_complete, task.is_cancelled " +
						"FROM task " +
						"WHERE task.is_complete = 0 " +
						"AND task.is_cancelled = 0;";
		Queries.run_DisplayQuery(query, conn);
	}

    public void addTask(String taskName){
        String query = "INSERT INTO task (task_label) values (" + "\"" + taskName + "\"" + ");";
		Queries.run_UpdateQuery(query, conn);
	}

    public void setDueDate(int taskNum, String dueDate) throws ParseException{
        String query = "UPDATE task SET task.due_date = " + "\"" + dueDate + "\"" + "WHERE task_id = " +
				"\"" + taskNum + "\"" + ";";
        Queries.run_UpdateQuery(query, conn);
    }

	/**
	 * FOR TESTING PURPOSES ONLY
	 */
	public void removeTask(String taskName){
    	String query = "DELETE FROM task WHERE task_label = " + "\"" +taskName + "\"" + ";";
    	Queries.run_UpdateQuery(query, conn);
    }


    public void addKeywords(int taskNum, String tags) {
		String query = "UPDATE task SET task.tag = " + "\"" + tags + "\"" + " WHERE task_id = " +
				"\"" + taskNum + "\"" + ";";
		Queries.run_UpdateQuery(query, conn);
	}

    public void markTaskComplete(int taskNum) {
    	String query = "UPDATE task SET task.is_complete = 1 WHERE task_id = " + taskNum + ";";
    	Queries.run_TaskCompleteQuery(query);
    }

    public void cancelTask(int taskNum){
    	String query = "UPDATE task SET task.is_cancelled = 1 WHERE task_id = " + taskNum + ";";
    	Queries.run_CancelTaskQuery(query);
    }

    public void showTask(int taskNum){
    	String query = "SELECT * FROM task WHERE task.task_id = " + taskNum + ";";
    	Queries.run_ShowTaskQuery(query);
    }

    public void showActiveForTag(String tag){
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 0 "
    			+ "AND task.is_cancelled = 0 "
    			+ "AND task.tag LIKE " + tag + ";";
    	Queries.run_ShowActiveTagQuery(query);
    }

    public void showCompletedTasks(){
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 1;";
    	Queries.run_CompletedTasksQuery(query);
    }

    public void showOverdueTasks(){
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date < TIMESTAMP() " +
    			"AND task.is_complete = 0;";
    	Queries.run_OverdueTasksQuery(query);
    }

    public void showDueToday() {
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date = EXTRACT DATE FROM TIMESTAMP();";
    	Queries.run_DueTodayQuery(query);

    }

    public void showDueSoon(){
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date <= DATEADD(day, 3, EXTRACT DATE FROM TIMESTAMP()) " +
    			"AND task.due_date >= EXTRACT DATE FROM TIMESTAMP();";
    	Queries.run_DueSoonQuery(query);
    }

    public void renameTask(int taskNum, String newLabel){
    	String query = "UPDATE task " +
    			"SET task.label = " + newLabel +
    			"WHERE task_id = " + taskNum + ";";
    	Queries.run_RenameQuery(query);
    }

    public void searchByKeyword(String keyword){
    	String query = "SELECT * FROM task " +
    			"WHERE task.task_label LIKE " + keyword + ";";
    	Queries.run_SearchKeywordQuery(query);
    }
}
