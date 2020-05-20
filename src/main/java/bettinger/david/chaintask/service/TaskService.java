package bettinger.david.chaintask.service;


import bettinger.david.chaintask.model.Task;
import bettinger.david.chaintask.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTaskForChain(){
        return taskRepository.findAll();
    }

}
