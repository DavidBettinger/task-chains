package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TaskDataToTask implements Converter<TaskData, Task> {

    private final CommentDataToComment commentDataConverter;

    public TaskDataToTask(CommentDataToComment commentDataConverter) {
        this.commentDataConverter = commentDataConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Task convert(TaskData taskData) {
        final Task task = new Task();
        task.setCompleted(taskData.getCompleted());
        task.setCreatedAt(taskData.getCreatedAt());
        task.setDeadline(taskData.getDeadline());
        task.setDescription(taskData.getDescription());
        task.setName(taskData.getName());
        task.setId(taskData.getId());
        task.setTaskNumber(taskData.getTaskNumber());

        if(taskData.getComments() != null && taskData.getComments().size() > 0){
            taskData.getComments()
                    .forEach(comment -> task.getComments().add(commentDataConverter.convert(comment)));
        }

        return task;
    }
}
