import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import asg.cliche.*;


public class Task {
    //Testing that I can push with this comment
    private int idStartValue = 0;

    private int id;
    private String label;
    private ArrayList<String> keywords;
    private String createDate; /* mm/dd/yyyy */
    private String dueDate; /* mm/dd/yyyy */
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
        String currentDate = new Date().toString();
        this.createDate = dateFormat.format(currentDate);
        this.isActive = true;
        this.isComplete = false;
        this.keywords = new ArrayList<String>();
        /* assign an id value and set so that new tasks
           can be assigned a sequential value
        */
        if(idStartValue == 0)
            this.id = idStartValue;
        else
            this.id = idStartValue++;
    }

    @Command(name = "setDate", description = "Sets the due date", header = "Set the due date")
    public void setDueDate(String dueDate) {
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

    public int getTaskID(){ return this.id; }

    public String getTaskLabel(){ return this.label; }

    public ArrayList<String> getKeywords(){ return this.keywords; }

    public String getCreateDate(){ return this.createDate; }

    public String getDueDate() { return this.dueDate; }

    public boolean getActiveStatus(){ return this.isActive; }

    public boolean getCompletionStatus() { return this.isComplete; }

}
