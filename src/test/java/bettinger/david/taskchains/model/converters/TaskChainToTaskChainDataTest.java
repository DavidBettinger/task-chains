package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.entity.Task;
import bettinger.david.taskchains.model.entity.TaskChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskChainToTaskChainDataTest {

    public static final TaskChain TASK_CHAIN = new TaskChain();
    public static final Date CREATED_AT = new Date();
    public static final Date DEADLINE = new Date();
    public static final String NAME = "test";
    public static final Boolean COMPLETED = false;


    TaskChainToTaskChainData taskChainConverter;

    @BeforeEach
    void setUp() {
        UserToUserData userConverter = new UserToUserData();
        taskChainConverter = new TaskChainToTaskChainData(new TaskToTaskData(new CommentToCommentData(userConverter)), userConverter);
        TASK_CHAIN.setCreatedAt(CREATED_AT);
        TASK_CHAIN.setDeadline(DEADLINE);
        TASK_CHAIN.setName(NAME);
        TASK_CHAIN.setCompleted(COMPLETED);
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(taskChainConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(taskChainConverter.convert(new TaskChain()));
    }

    @Test
    void convert() {
        //given
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setName(NAME);
        tasks.add(task);
        TASK_CHAIN.setTasks(tasks);

        //when
        TaskChainData taskChainData = taskChainConverter.convert(TASK_CHAIN);

        //then
        assertNotNull(taskChainData);
        assertNotNull(taskChainData.getTasks());
        assertEquals(taskChainData.getDeadline(), DEADLINE);
        assertEquals(taskChainData.getCreatedAt(), CREATED_AT);
        assertEquals(taskChainData.getName(), NAME);
        assertEquals(taskChainData.getCompleted(), COMPLETED);
        assertEquals(taskChainData.getTasks().get(0).getName(), NAME);
    }

    @Test
    void convertWithNullTask() {
        //given
        TASK_CHAIN.setTasks(null);

        //when
        TaskChainData taskChainData = taskChainConverter.convert(TASK_CHAIN);

        //then
        assertNotNull(taskChainData);
        assertNotNull(taskChainData.getTasks());
        assertEquals(taskChainData.getDeadline(), DEADLINE);
        assertEquals(taskChainData.getCreatedAt(), CREATED_AT);
        assertEquals(taskChainData.getName(), NAME);
        assertEquals(taskChainData.getCompleted(), COMPLETED);
    }










}