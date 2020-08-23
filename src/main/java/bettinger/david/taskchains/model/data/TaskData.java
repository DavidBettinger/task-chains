package bettinger.david.taskchains.model.data;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TaskData extends BaseData{

    private String name;

    private String description;

    private Boolean completed;

    private int taskNumber;

    private Date createdAt;

    private Date deadline;

    private List<CommentData> comments = new ArrayList<>();

    public TaskData() {
        super(null);
    }

    public TaskData(Long id, String name, String description, List<CommentData> comments, Boolean completed, int taskNumber, Date createdAt, Date deadline) {
        super(id);
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.taskNumber = taskNumber;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.comments = comments;
    }
}
