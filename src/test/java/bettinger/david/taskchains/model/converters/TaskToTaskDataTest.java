package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.model.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskToTaskDataTest {

    public static final Task TASK = new Task();
    public static final Date CREATED_AT = new Date();
    public static final Date DEADLINE = new Date();
    public static final String NAME = "test";
    public static final Boolean COMPLETED = false;


    TaskToTaskData taskConverter;

    @BeforeEach
    void setUp() {
        taskConverter = new TaskToTaskData(new CommentToCommentData(new UserToUserData()));
        TASK.setCreatedAt(CREATED_AT);
        TASK.setDeadline(DEADLINE);
        TASK.setName(NAME);
        TASK.setCompleted(COMPLETED);
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(taskConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(taskConverter.convert(new Task()));
    }

    @Test
    void convert() {
        //given: setUp


        //when
        TaskData taskData = taskConverter.convert(TASK);

        //then
        assertNotNull(taskData);
        assertEquals(taskData.getDeadline(), DEADLINE);
        assertEquals(taskData.getCreatedAt(), CREATED_AT);
        assertEquals(taskData.getName(), NAME);
        assertEquals(taskData.getCompleted(), COMPLETED);
    }
}