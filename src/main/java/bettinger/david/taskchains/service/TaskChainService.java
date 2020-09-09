package bettinger.david.taskchains.service;

import bettinger.david.taskchains.model.converters.TaskChainDataToTaskChain;
import bettinger.david.taskchains.model.converters.TaskChainToTaskChainData;
import bettinger.david.taskchains.model.converters.TaskDataToTask;
import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import bettinger.david.taskchains.model.entity.TaskChain;
import bettinger.david.taskchains.repository.TaskChainRepository;
import bettinger.david.taskchains.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class TaskChainService {

    private final TaskChainRepository taskChainRepository;
    private final TaskRepository taskRepository;
    private final TaskChainToTaskChainData taskChainConverter;
    private final TaskChainDataToTaskChain taskChainDataConverter;
    private final TaskDataToTask taskDataConverter;

    public TaskChainService(TaskChainRepository taskChainRepository,
                            TaskRepository taskRepository, TaskChainToTaskChainData taskChainConverter,
                            TaskChainDataToTaskChain taskChainDataConverter, TaskDataToTask taskConverter) {
        this.taskChainRepository = taskChainRepository;
        this.taskRepository = taskRepository;
        this.taskChainConverter = taskChainConverter;
        this.taskChainDataConverter = taskChainDataConverter;
        this.taskDataConverter = taskConverter;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<TaskChainData> findAll() {

        List<TaskChain> taskChains = taskChainRepository.findAll();
        taskChains.forEach(taskChain -> sortTasks(taskChain.getTasks()));

        ArrayList<TaskChainData> taskChainDataList = new ArrayList<>();

        taskChains.forEach(taskChain -> taskChainDataList.add(taskChainConverter.convert(taskChain)));

        return taskChainDataList;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<TaskChainData> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAll();
        } else {
            List<TaskChain> taskChains = taskChainRepository.search(filterText);
            taskChains.forEach(taskChain -> sortTasks(taskChain.getTasks()));

            ArrayList<TaskChainData> taskChainDataList = new ArrayList<>();

            taskChains.forEach(taskChain -> taskChainDataList.add(taskChainConverter.convert(taskChain)));

            return taskChainDataList;
        }
    }

    public long count() {
        return taskChainRepository.count();
    }

    public void delete(TaskChainData taskChainData) {
        log.debug("deleted taskChain with name: " + taskChainData.getName() + " and id: " + taskChainData.getId());
        taskChainRepository.delete(Objects.requireNonNull(taskChainDataConverter.convert(taskChainData)));
    }

    @Transactional
    public TaskChainData deleteTask(TaskChainData taskChainData, TaskData taskData) {
        if (taskChainData == null) {
            log.error("ChainTask is null. Are you sure you have connected your form to the application?");
            return null;
        } else if (taskData == null) {

            log.error("Task is null. Are you sure you have connected your form to the application?");
            return taskChainData;

        }

        TaskChain detachedTaskChain = taskChainDataConverter.convert(taskChainData);

        Task detachedTask = taskDataConverter.convert(taskData);

        assert detachedTaskChain != null;
        detachedTaskChain.getTasks().remove(detachedTask);

        assert detachedTask != null;
        updateTaskNumbers(detachedTaskChain.getTasks(), detachedTask.getTaskNumber());

        TaskChain savedTaskChain = taskChainRepository.save(detachedTaskChain);
        sortTasks(savedTaskChain.getTasks());
        TaskChainData savedTaskChainData = taskChainConverter.convert(savedTaskChain);

        if (detachedTask.getId() != null) {
            taskRepository.delete(detachedTask);
        }


        return savedTaskChainData;
    }

    private void sortTasks(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::getTaskNumber));
    }

    private void updateTaskNumbers(List<Task> tasks, int index) {
        sortTasks(tasks);
        for (int i = index; i < tasks.size(); i++) {
            tasks.get(i).setTaskNumber(i);
        }
    }


    /**
     * @param taskChainData that was changed
     * @param index         the index of the taskData in the taskChainData that was added to the taskChainData
     *                      the taskNumber of the tasks will be updated
     * @return the saved taskChainData
     */
    @Transactional
    public TaskChainData save(TaskChainData taskChainData, int index) {
        if (taskChainData == null) {
            log.error("ChainTask is null. Are you sure you have connected your form to the application?");
            return null;
        }

        TaskChain detachedTaskChain = taskChainDataConverter.convert(taskChainData);

        assert detachedTaskChain != null;
        updateTaskNumbers(detachedTaskChain.getTasks(), index + 1);

        TaskChain savedTaskChain = taskChainRepository.save(detachedTaskChain);
        sortTasks(savedTaskChain.getTasks());

        log.debug("Saved taskChain with name: " + savedTaskChain.getName() + " and id: " + savedTaskChain.getId());
        return taskChainConverter.convert(savedTaskChain);
    }

    @Transactional
    public TaskChainData save(TaskChainData taskChainData) {
        if (taskChainData == null) {
            log.error("ChainTask is null. Are you sure you have connected your form to the application?");
            return null;
        }

        TaskChain detachedTaskChain = taskChainDataConverter.convert(taskChainData);
        TaskChain savedTaskChain = taskChainRepository.save(detachedTaskChain);
        sortTasks(savedTaskChain.getTasks());

        log.debug("Saved taskChain with name: " + savedTaskChain.getName() + " and id: " + savedTaskChain.getId());
        return taskChainConverter.convert(savedTaskChain);
    }

}
