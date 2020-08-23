package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataToTaskTest {

    public static final TaskData TASK_DATA = new TaskData();
    public static final Date CREATED_AT = new Date();
    public static final Date DEADLINE = new Date();
    public static final String NAME = "test";
    public static final Boolean COMPLETED = false;


    TaskDataToTask taskDataConverter;

    @BeforeEach
    void setUp() {
        taskDataConverter = new TaskDataToTask(new CommentDataToComment(new UserDataToUser()));
        TASK_DATA.setCreatedAt(CREATED_AT);
        TASK_DATA.setDeadline(DEADLINE);
        TASK_DATA.setName(NAME);
        TASK_DATA.setCompleted(COMPLETED);
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(taskDataConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(taskDataConverter.convert(new TaskData()));
    }

    @Test
    void convert() {
        //given: setUp


        //when
        Task task = taskDataConverter.convert(TASK_DATA);

        //then
        assertNotNull(task);
        assertEquals(task.getDeadline(), DEADLINE);
        assertEquals(task.getCreatedAt(), CREATED_AT);
        assertEquals(task.getName(), NAME);
        assertEquals(task.getCompleted(), COMPLETED);
    }
}