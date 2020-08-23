package bettinger.david.taskchains.model.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class TaskChainData extends BaseData{

    private Date createdAt;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private String name;

    private String description;

    private Boolean completed;

    private UserData createdBy;

    private List<TaskData> tasks = new ArrayList<>();

    public TaskChainData(){
        super(null);
    }

    public TaskChainData(Long id, Date createdAt, Date deadline, String name, String description, Boolean completed, List<TaskData> tasks, UserData createdBy) {
        super(id);
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.tasks = tasks;
        this.createdBy = createdBy;
    }
}
