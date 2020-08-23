package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.TaskChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskChainDataToTaskChainTest {
    public static final TaskChainData TASK_CHAIN_DATA = new TaskChainData();
    public static final Date CREATED_AT = new Date();
    public static final Date DEADLINE = new Date();
    public static final String NAME = "test";
    public static final Boolean COMPLETED = false;


    TaskChainDataToTaskChain taskChainDataConverter;

    @BeforeEach
    void setUp() {
        UserDataToUser userDataConverter = new UserDataToUser();
        taskChainDataConverter = new TaskChainDataToTaskChain(new TaskDataToTask(new CommentDataToComment(userDataConverter)), userDataConverter);
        TASK_CHAIN_DATA.setCreatedAt(CREATED_AT);
        TASK_CHAIN_DATA.setDeadline(DEADLINE);
        TASK_CHAIN_DATA.setName(NAME);
        TASK_CHAIN_DATA.setCompleted(COMPLETED);
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(taskChainDataConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(taskChainDataConverter.convert(new TaskChainData()));
    }

    @Test
    void convert() {
        //given
        List<TaskData> taskDatas = new ArrayList<>();
        TaskData taskData = new TaskData();
        taskData.setName(NAME);
        taskDatas.add(taskData);
        TASK_CHAIN_DATA.setTasks(taskDatas);

        //when
        TaskChain taskChain = taskChainDataConverter.convert(TASK_CHAIN_DATA);

        //then
        assertNotNull(taskChain);
        assertNotNull(taskChain.getTasks());
        assertEquals(taskChain.getDeadline(), DEADLINE);
        assertEquals(taskChain.getCreatedAt(), CREATED_AT);
        assertEquals(taskChain.getName(), NAME);
        assertEquals(taskChain.getCompleted(), COMPLETED);
        assertEquals(taskChain.getTasks().get(0).getName(), NAME);
    }

    @Test
    void convertWithNullTask() {
        //given
        TASK_CHAIN_DATA.setTasks(null);

        //when
        TaskChain taskChain = taskChainDataConverter.convert(TASK_CHAIN_DATA);

        //then
        assertNotNull(taskChain);
        assertNotNull(taskChain.getTasks());
        assertEquals(taskChain.getDeadline(), DEADLINE);
        assertEquals(taskChain.getCreatedAt(), CREATED_AT);
        assertEquals(taskChain.getName(), NAME);
        assertEquals(taskChain.getCompleted(), COMPLETED);
    }
}