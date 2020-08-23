package bettinger.david.taskchains.bootstrap;

import bettinger.david.taskchains.model.entity.Task;
import bettinger.david.taskchains.model.entity.TaskChain;
import bettinger.david.taskchains.model.entity.User;
import bettinger.david.taskchains.repository.TaskChainRepository;
import bettinger.david.taskchains.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BootStrapData implements CommandLineRunner {
    private final TaskChainRepository taskChainRepository;
    private final UserRepository userRepository;

    public BootStrapData(TaskChainRepository taskChainRepository, UserRepository userRepository) {
        this.taskChainRepository = taskChainRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setUserName("david@mail.de");
        user.setFirstName("David");
        user.setName("Bettinger");
        user.setPassword("12345");
        user.setRole("Admin");

        userRepository.save(user);

        TaskChain taskChain2 = new TaskChain();
        taskChain2.setDeadline(new Date());
        taskChain2.setCreatedBy(user);
        taskChain2.setName("Neuer Mitarbeiter");
        taskChain2.setDescription("Schritte zum einarbeiten eines neuen Mitarbeiters");



        Task task1 = new Task();
        task1.setTaskNumber(0);
        task1.setDeadline(new Date());
        task1.setName("Übersicht");
        task1.setDescription("Wie ist Insel e.V. aufgebaut");

        taskChain2.getTasks().add(task1);

        Task task2 = new Task();
        task2.setTaskNumber(1);
        task2.setDeadline(new Date());
        task2.setName("Übersicht Programme");
        task2.setDescription("Welche Programme werden benutzt.");

        taskChain2.getTasks().add(task2);

        taskChainRepository.save(taskChain2);

        System.out.println(userRepository.findByUserName("david@mail.de").get(0).toString());
    }
}
