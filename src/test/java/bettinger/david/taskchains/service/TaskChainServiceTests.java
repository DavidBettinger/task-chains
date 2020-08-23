package bettinger.david.taskchains.service;

import bettinger.david.taskchains.model.converters.*;
import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import bettinger.david.taskchains.model.entity.TaskChain;
import bettinger.david.taskchains.repository.TaskChainRepository;
import bettinger.david.taskchains.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TaskChainServiceTests {

    public static final String TASK_NAME = "task name";
    public static final String TASK_CHAIN_NAME = "task chain name";

    @Mock
    TaskChainRepository taskChainRepository;

    @Mock
    TaskRepository taskRepository;


    TaskChainService taskChainService;

    @BeforeEach
    public void setUp() {
        UserDataToUser userDataConverter = new UserDataToUser();
        UserToUserData userConverter = new UserToUserData();
        TaskDataToTask taskDataToTask = new TaskDataToTask(new CommentDataToComment(userDataConverter));
        TaskToTaskData taskToTaskData = new TaskToTaskData(new CommentToCommentData(userConverter));
        taskChainService = new TaskChainService(taskChainRepository, taskRepository, new TaskChainToTaskChainData(taskToTaskData, userConverter),
                new TaskChainDataToTaskChain(taskDataToTask, userDataConverter), taskDataToTask);

    }

    @Test
    void findAll() {
        List<TaskChain> returnTaskChainList = new ArrayList<>();
        TaskChain taskChain1 = new TaskChain();
        taskChain1.setName(TASK_CHAIN_NAME);
        TaskChain taskChain2 = new TaskChain();
        taskChain2.setName(TASK_CHAIN_NAME);
        returnTaskChainList.add(taskChain1);
        returnTaskChainList.add(taskChain2);

        when(taskChainRepository.findAll()).thenReturn(returnTaskChainList);

        List<TaskChainData> taskChainDataList = taskChainService.findAll();

        assertNotNull(taskChainDataList);
        assertEquals(2, taskChainDataList.size());
    }

    @Test
    void deleteTask() {

        TaskChain taskChain = new TaskChain();
        taskChain.setName(TASK_CHAIN_NAME);
        taskChain.setId(1L);
        Task task = new Task();
        task.setName(TASK_NAME);
        task.setTaskNumber(0);
        taskChain.getTasks().add(task);

        TaskChainData taskChainData = new TaskChainData();
        taskChainData.setName(TASK_CHAIN_NAME);
        taskChainData.setId(1L);
        TaskData taskData1 = new TaskData();
        taskData1.setTaskNumber(0);
        taskData1.setName(TASK_NAME);
        TaskData taskData2 = new TaskData();
        taskData2.setTaskNumber(1);
        taskData2.setName(TASK_NAME);
        taskChainData.getTasks().add(taskData1);
        taskChainData.getTasks().add(taskData2);

        when(taskChainRepository.save(any())).thenReturn(taskChain);


        TaskChainData returnedTaskChainData = taskChainService.deleteTask(taskChainData, taskData1);

        assertNotNull(returnedTaskChainData);
        assertNotNull(returnedTaskChainData.getTasks());
        assertEquals(1, returnedTaskChainData.getTasks().size());
        assertEquals(0, returnedTaskChainData.getTasks().get(0).getTaskNumber());
    }


}
