package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.entity.TaskChain;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TaskChainDataToTaskChain implements Converter<TaskChainData, TaskChain> {

    private final TaskDataToTask taskDataConverter;
    private final UserDataToUser userDataConverter;

    public TaskChainDataToTaskChain(TaskDataToTask taskDataConverter, UserDataToUser userDataConverter) {
        this.taskDataConverter = taskDataConverter;
        this.userDataConverter = userDataConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public TaskChain convert(TaskChainData taskChainData) {
        final TaskChain taskChain = new TaskChain();
        taskChain.setCompleted(taskChainData.getCompleted());
        taskChain.setCreatedAt(taskChainData.getCreatedAt());
        taskChain.setDeadline(taskChainData.getDeadline());
        taskChain.setDescription(taskChainData.getDescription());
        taskChain.setName(taskChainData.getName());
        if (taskChainData.getTasks() != null && taskChainData.getTasks().size() > 0) {
            taskChainData.getTasks()
                    .forEach(task -> taskChain.getTasks().add(taskDataConverter.convert(task)));
        }

        taskChain.setCreatedBy(userDataConverter.convert(taskChainData.getCreatedBy()));

        taskChain.setId(taskChainData.getId());
        return taskChain;
    }
}
