import java.text.ParseException;
import java.util.LinkedList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

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
    private static Connection conn;


    public TaskTracker(Connection conn){
    	this.conn = conn;
        taskList = new LinkedList<>();
        today = new Date(); // gets the current date and time
        activeTasks = "------------------------- ACTIVE TASKS -------------------------\n";

    }

    public void addTask(String taskName){
        //Task newTask = new Task(taskName);
        //newTask.setTaskID(currentTaskID);
        String query = "INSERT INTO task (task_label) values (" + taskName + ");";
        runQuery(query);
        //taskList.add(newTask);
        String returnQuery = "SELECT task_id FROM task WHERE task_label = " + taskName + ";";
        runQuery(returnQuery);
        //return ;
    }

    // Setting return value to String so an error message can be returned
    public void setDueDate(int taskNum, String dueDate) throws ParseException{
        Date date_due = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
        String query = "UPDATE task SET task.due_date = " + date_due + "WHERE task_id = " + taskNum + ";";
        runQuery(query);				
//        for(Task task:taskList){
//            if(task.getTaskID() == taskNum)
//                task.setDueDate(date_due);
//            else return "No tasks in the list correspond to ID value" + taskNum;
//        }
       // return "";
    }

    public void removeTask(String taskName){
//        for(Task task : taskList){
//            if(task.getTaskLabel().equals(taskName)){
//                taskList.remove();
//            }
//        }
    	String query = "DELET FROM task WHERE task_label = " + taskName + ";";
    	runQuery(query);
    }

    public void showActiveTasks(){
       // for(Task task:taskList){
            //if(task.getActiveStatus() == true) {
            	String query = 
    			"SELECT task.task_id, task.task_label, task.time_stamp, task.due_date " +
    			"FROM task " + 
    			"WHERE task.is_complete = 0 " + 
    			"AND task.is_cancelled = 0;";
            	runQuery(query);
                //activeTasks += task.showDetails();
            //}
       // }
       // return activeTasks;
    }

    public void addKeywords(int taskNum, String tags){
//        for(Task task:taskList){
//            if(task.getTaskID() == taskNum)
//                    task.addKeyword(tags);
//            else
//                return "No tasks in the list correspond to ID value" + taskNum + ".";
//        }
    	String query = "UPDATE task SET task.tag = " + tags + " WHERE task_id = " + taskNum + ";";
        runQuery(query);
    	//return "";
    }

    public void markTaskComplete(int taskNum) {
//        for (Task task : taskList) {
//            if (task.getTaskID() == taskNum) {
//                if (task.getCompletionStatus() == false)
//                    task.finishTask();
//                else
//                    return "Task " + taskNum + " is already complete";
//            }else
//                return "No tasks in the list correspond to ID value" + taskNum + ".";
//        }
    	String query = "UPDATE task SET task.is_complete = 1 WHERE task_id = " + taskNum + ";";
    	runQuery(query);
       // return "";
    }

    public void cancelTask(int taskNum){
//        for(Task task:taskList){
//            if(task.getTaskID() == taskNum){
//                task.cancelTask();
//            } else return "No tasks in the list correspond to ID value" + taskNum + ".";
//        }
    	String query = "UPDATE task SET task.is_cancelled = 1 WHERE task_id = " + taskNum + ";";
    	runQuery(query);
        //return "";
    }

    public void showTask(int taskNum){
//        for(Task task:taskList){
//            if(task.getTaskID() == taskNum){
//                task.showDetails();
//            }else return "No tasks in the list correspond to ID value" + taskNum + ".";
//        }
    	String query = "SELECT * FROM task WHERE task.task_id = " + taskNum + ";";
    	runQuery(query);
       // return "";
    }

    public void showActiveForTag(String tag){
//        LinkedList<Task> activeTasksAssociated_Tag = new LinkedList<>();
//        for(Task task:taskList){
//            if(task.getKeywords().contains(tag))
//                activeTasksAssociated_Tag.add(task);
//            else return "No tasks in the list have '" + tag + "' tag associated with them.";
//        }
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 0 "
    			+ "AND task.is_cancelled = 0 "
    			+ "AND task.tag LIKE " + tag + ";";
    	runQuery(query);
       // return activeTasksAssociated_Tag.toString();
    }

    public void showCompletedTasks(){
//        String completedTasks = "";
//        for(Task task: taskList){
//            if(task.getCompletionStatus() == true){
//                completedTasks.concat(task.getTaskLabel());
//            }
//        }
    	String query = "SELECT * FROM task "
    			+ "WHERE task.is_complete = 1;";
    	runQuery(query);
        //return completedTasks;
    }

    public void showOverdueTasks(){
//        String overdueTasks = "";
//        for(Task task:taskList){
//            if(task.getDueDate().before(today))
//                overdueTasks += task.showDetails();
//        }
//        if(overdueTasks.equals(""))
//            return "No overdue tasks.";
    	String query = "SELECT * FROM task " + 
    			"WHERE task.due_date < TIMESTAMP() " + 
    			"AND task.is_complete = 0;";
    	runQuery(query);
        //return "";
    }
    
    public void showDueToday() {
    	String query = "SELECT * FROM task " + 
    			"WHERE task.due_date = EXTRACT DATE FROM TIMESTAMP();";
    	runQuery(query);
    	
    }

    public void showDueSoon(){
//        String dueSoon = "";
//        Date tomorrow = new Date(System.currentTimeMillis() + 86400000);
//        Date inTwoDays = new Date(System.currentTimeMillis() + 86400000 * 2);
//        Date inThreeDays = new Date(System.currentTimeMillis() + 86400000 * 3);
//        for(Task task:taskList){
//            Date dueDate = task.getDueDate();
//            if(dueDate.equals(today) || dueDate.equals(tomorrow) ||
//                    dueDate.equals(inTwoDays) || dueDate.equals(inThreeDays)){
//                dueSoon += task.showDetails();
//            }
//        }
//        if(dueSoon.equals(""))
//            return "No tasks are due within the next three days.";
    	String query = "SELECT * FROM task " + 
    			"WHERE task.due_date <= DATEADD(day, 3, EXTRACT DATE FROM TIMESTAMP()) " + 
    			"AND task.due_date >= EXTRACT DATE FROM TIMESTAMP();";
    	runQuery(query);
       // return dueSoon;
    }

    public String renameTask(int taskNum, String newLabel){
//        for(Task task:taskList){
//            if(task.getTaskID() == taskNum){
//                task.setTaskLabel(newLabel);
//            } else return "No tasks in the list correspond to ID value" + taskNum + ".";
//        }
    	String query = "UPDATE task " + 
    			"SET task.label = " + newLabel + 
    			"WHERE task_id = " + taskNum + ";";
    	runQuery(query);
        return "";
    }

    public void searchByKeyword(String keyword){
//        String tasksWithKeyword = "";
//        for(Task task:taskList){
//            if(task.getKeywords().contains(keyword))
//                tasksWithKeyword += task.showDetails();
//        }
//        if(tasksWithKeyword.equals(""))
//            return "No tasks in the list contain the keyword: " + keyword + ".";
    	
    	String query = "SELECT * FROM task " + 
    			"WHERE task.task_label LIKE " + keyword + ";";
    	runQuery(query);
       // return tasksWithKeyword;
    }

    public static void runQuery(String query) {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			//String query = "SELECT * FROM Persons where LastName = ?";
			String name = "Data";

			stmt = conn.prepareStatement(query);
			stmt.setString(1, name);
			rs = stmt.executeQuery();

			// Now do something with the ResultSet ....
			boolean rowsLeft = true;
			rs.first();
			while (rowsLeft) {
				System.out.println(rs.getInt(1) + ":" + rs.getString(2) + ":" + rs.getString(3) + ":" + rs.getString(4)
						+ ":" + rs.getString(5));
				rowsLeft = rs.next();
			}
		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			// it is a good idea to release resources in a finally{} block
			// in reverse-order of their creation if they are no-longer needed
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

}
