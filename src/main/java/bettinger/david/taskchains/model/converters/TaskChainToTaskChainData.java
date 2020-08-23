package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.entity.TaskChain;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TaskChainToTaskChainData implements Converter<TaskChain, TaskChainData> {

    private final TaskToTaskData taskConverter;
    private final UserToUserData userConverter;

    public TaskChainToTaskChainData(TaskToTaskData taskConverter, UserToUserData userConverter) {
        this.taskConverter = taskConverter;
        this.userConverter = userConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public TaskChainData convert(TaskChain taskChain) {

        TaskChainData taskChainData = new TaskChainData();
        taskChainData.setId(taskChain.getId());
        taskChainData.setCreatedAt(taskChain.getCreatedAt());
        taskChainData.setDeadline(taskChain.getDeadline());
        taskChainData.setName(taskChain.getName());
        taskChainData.setDescription(taskChain.getDescription());
        taskChainData.setCompleted(taskChain.getCompleted());
        if(taskChain.getTasks() != null && taskChain.getTasks().size() > 0){
            taskChain.getTasks()
                    .forEach(task -> taskChainData.getTasks().add(taskConverter.convert(task)));
        }

        taskChainData.setCreatedBy(userConverter.convert(taskChain.getCreatedBy()));

        return taskChainData;
    }
}
