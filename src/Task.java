import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import asg.cliche.*;

public class Task {
    private int id = 1;
    private String label;
    private ArrayList<String> keywords;
    private Date createDate; /* mm/dd/yyyy */
    private Date dueDate; /* mm/dd/yyyy */
    private boolean isActive; // 1 if active, else 0
    private boolean isComplete; // 1 if complete, else 0
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Task constructor. We pass in a label to associate our new task with
     * @param label
     */
    public Task(String label){
        this.label = label; // init using the parameter

        // using Date will let us always get the current date
        this.createDate = new Date();
        this.isActive = true;
        this.isComplete = false;
        this.keywords = new ArrayList<String>();
        /* assign an id value and set so that new tasks
           can be assigned a sequential value
        */
    }

    public void setTaskID(int ID){
        this.id = id;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void cancelTask(){
        this.isActive = false;
    }

    public void finishTask(){
        this.isComplete = true;
    }

    public void addKeyword(String keyword){
        this.keywords.add(keyword);
    }

    public String showDetails(){
        return "Task ID: " + id + "\nLabel: " + label + "\nCreation Date: " + createDate + "\nDue Date: " + dueDate + "\n\n";
    }

    public int getTaskID(){ return this.id; }

    public String getTaskLabel(){ return this.label; }

    public void setTaskLabel(String newLabel) {this.label = newLabel;}

    public ArrayList<String> getKeywords(){ return this.keywords; }

    public Date getCreateDate(){ return this.createDate; }

    public Date getDueDate() { return this.dueDate; }

    public boolean getActiveStatus(){ return this.isActive; }

    public boolean getCompletionStatus() { return this.isComplete; }

}
