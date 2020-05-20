package bettinger.david.chaintask.bootstrap;

import bettinger.david.chaintask.model.ChainTask;
import bettinger.david.chaintask.repository.ChainTaskRepository;
import bettinger.david.chaintask.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BootStrapData implements CommandLineRunner {
    private final ChainTaskRepository chainTaskRepository;
    private final TaskRepository taskRepository;

    public BootStrapData(ChainTaskRepository chainTaskRepository, TaskRepository taskRepository) {
        this.chainTaskRepository = chainTaskRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ChainTask chainTask = new ChainTask();
        chainTask.setDeadline(new Date());
        chainTask.setChainName("Test");
        chainTask.setDescription("Test test");

        chainTaskRepository.save(chainTask);
    }
}
