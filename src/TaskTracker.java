import java.text.ParseException;
import java.sql.*;

/**
 * An all purpose class that will create a list to keep track of
 * task objects, which will be part of the backend for the to-do list
 * manager.
 *
 * @author Stephanie Labastida
 */
public class TaskTracker {
    private static Connection conn;


    public TaskTracker(Connection conn){
    	this.conn = conn;
    }

    // works, displays in order by date, with null due dates being last
	public void showActiveTasks(){
		// for(Task task:taskList){
		//if(task.getActiveStatus() == true) {
		String query =
				"SELECT task.task_id, task.task_label, task.time_stamp, task.due_date, task.tag, task.is_complete, task.is_cancelled " +
						"FROM task " +
						"WHERE task.is_complete = 0 " +
						"AND task.is_cancelled = 0 " +
						"order by -due_date desc;";
		Queries.run_DisplayQuery(query, conn);
	}

	// works
    public void addTask(String taskName){
        String query = "INSERT INTO task (task_label) values (" + "\"" + taskName + "\"" + ");";
		Queries.run_UpdateQuery(query, conn);
	}

	// works
    public void setDueDate(int taskNum, String dueDate) throws ParseException{
        String query = "UPDATE task SET task.due_date = " + "\"" + dueDate + "\"" + "WHERE task_id = " +
				"\"" + taskNum + "\"" + ";";
        Queries.run_UpdateQuery(query, conn);
    }

	// helper for cancel
	private void removeTask(int taskNum){
    	String query = "DELETE FROM task WHERE task_id = " + "\"" +taskNum + "\"" + ";";
    	Queries.run_UpdateQuery(query, conn);
    }

    public void addKeywords(int taskNum, String tags) {

		String query = "UPDATE task " +
				"SET tag = CASE " +
				"WHEN (SELECT tag) IS NULL " +
				"THEN " + "\" " + tags + " \"" +
				"ELSE concat(tag, " + "\" " + tags + " \"" + ") " +
				"END " +
				"WHERE task_id = " + taskNum + ";";
		Queries.run_UpdateQuery(query, conn);
	}

	// works
    public void markTaskComplete(int taskNum) {
    	String query = "UPDATE task SET task.is_complete = 1 WHERE task_id = " + "\"" + taskNum + "\"" + ";";
    	Queries.run_UpdateQuery(query, conn);
    }

    // works
    public void cancelTask(int taskNum){
    	String query = "UPDATE task SET task.is_cancelled = 1 WHERE task_id = " + "\"" + taskNum + "\"" + ";";
    	Queries.run_UpdateQuery(query, conn);
		removeTask(taskNum);
    }

    // works
    public void showTask(int taskNum){
    	String query = "SELECT * FROM task WHERE task.task_id = " + "\"" +  taskNum + "\"" + ";";
    	Queries.run_DisplayQuery(query, conn);
    }

    // works
    public void showActiveForTag(String tag){
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 0 "
    			+ "AND task.is_cancelled = 0 "
    			+ "AND task.tag LIKE " + "\"%" + tag + "%\"" + ";";
    	Queries.run_DisplayQuery(query, conn);
    }

    // works
    public void showCompletedTasks_ByTag(String tag){
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 1" + " AND tag = " + "\"" + tag + "\"" + ";";
    	Queries.run_DisplayQuery(query, conn);
    }

    // works
    public void showOverdueTasks(){
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date < curdate() " +
    			"AND task.is_complete = 0;";
    	Queries.run_DisplayQuery(query, conn);
    }


	public void showDueToday() {
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date = curdate()";
    	Queries.run_DisplayQuery(query, conn);
    }

    // works
    public void showDueSoon(){
    	String query = "SELECT * FROM task " +
    			"WHERE task.due_date <= date_add(curdate(), interval 3 DAY) " +
    			"AND task.due_date >= curdate() AND is_complete = 0 ORDER BY -due_date desc;";
    	Queries.run_DisplayQuery(query, conn);
    }

    // works
    public void renameTask(int taskNum, String newLabel){
    	String query = "UPDATE task " +
    			"SET task_label = " + "\"" + newLabel + "\"" +
    			"WHERE task_id = " + "\"" + taskNum + "\"" + ";";
    	Queries.run_UpdateQuery(query, conn);
    }

    // works
    public void searchByKeyword(String keyword){
    	String query = "SELECT * FROM task "
				+ "WHERE task.tag LIKE " + "\"%" + keyword + "%\"" + ";";
    	Queries.run_DisplayQuery(query, conn);
    }
}
