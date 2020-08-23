package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskData implements Converter<Task, TaskData> {

    private final CommentToCommentData commentConverter;

    public TaskToTaskData(CommentToCommentData commentConverter) {
        this.commentConverter = commentConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public TaskData convert(Task task) {

        TaskData taskData = new TaskData();
        taskData.setId(task.getId());
        taskData.setDeadline(task.getDeadline());
        taskData.setName(task.getName());
        taskData.setDescription(task.getDescription());
        taskData.setCompleted(task.getCompleted());
        taskData.setTaskNumber(task.getTaskNumber());
        taskData.setCreatedAt(task.getCreatedAt());

        if(task.getComments() != null && task.getComments().size() > 0){
            task.getComments()
                    .forEach(comment -> taskData.getComments().add(commentConverter.convert(comment)));
        }

        return taskData;
    }
}
