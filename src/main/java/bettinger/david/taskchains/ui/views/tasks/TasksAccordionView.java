package bettinger.david.taskchains.ui.views.tasks;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.TaskData;
import bettinger.david.taskchains.service.TaskChainDataSessionService;
import bettinger.david.taskchains.service.TaskChainService;
import bettinger.david.taskchains.ui.MainLayout;
import bettinger.david.taskchains.ui.views.AccordionExtended;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(value = "tasks", layout = MainLayout.class)
@PageTitle("Admin | Tasks")
public class TasksAccordionView extends VerticalLayout {
    private final TaskChainService taskChainService;
    private final TaskChainDataSessionService taskChainDataSessionService;

    private final H2 h2 = new H2();

    private final List<TaskForm> taskFormList = new ArrayList<>();

    private TaskChainData taskChainData;

    private final AccordionExtended accordion = new AccordionExtended();


    public TasksAccordionView(TaskChainService taskChainService, TaskChainDataSessionService taskChainDataSessionService) {
        this.taskChainService = taskChainService;
        this.taskChainDataSessionService = taskChainDataSessionService;

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        System.out.println("onAttach event triggert");
        taskChainData = taskChainDataSessionService.getTaskChainData();
        if (taskChainData == null) {
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }
        h2.setText("Add tasks to " + taskChainData.getName());

        int indexEnd = taskChainData.getTasks().size();

        for (TaskData taskData : taskChainData.getTasks()) {
            addTask(taskData, indexEnd);
        }

        addNewTask(indexEnd);
        System.out.println(accordion.getElement().getChildCount());
        add(h2, accordion);
        setSizeFull();
    }

    private void addNewTask(int index){

        AccordionPanel accordionPanel = addTask(null, index);
        accordion.open(accordionPanel);
    }

    private void addListeners(TaskForm taskForm) {
        taskForm.addListener(TaskForm.SaveEvent.class, this::saveTask);
        taskForm.addListener(TaskForm.DeleteEvent.class, this::deleteTask);
//        taskForm.addListener(TaskChainsForm.CloseEvent.class, e -> closeEditor());
        taskChainDataSessionService.getTaskChainData();
    }

    private AccordionPanel addTask(TaskData taskData, int index) {
        TaskForm taskForm = new TaskForm();
        String name;
        if (taskData == null) {
            taskData = new TaskData();
            taskData.setDeadline(new Date());
            taskData.setTaskNumber(index);
            taskChainData.getTasks().add(index, taskData);
            taskFormList.add(index, taskForm);
            name = "New Task";
        } else {
            name = taskData.getName();
            taskFormList.add(taskForm);
        }

        taskForm.setTask(taskData);
        addListeners(taskForm);
        AccordionPanel accordionPanel = accordion.addAt(name, taskForm, index);

        accordionPanel.addOpenedChangeListener(openedChangeEvent -> {
            final TaskForm form = (TaskForm) openedChangeEvent.getSource().getContent().findFirst().get();
            String taskName = form.getTaskName();
            if (taskName != null && taskName.length() > 0) {
                openedChangeEvent.getSource().setSummaryText(taskName);
            }
        });

        return accordionPanel;
    }

    private void deleteTask(TaskForm.DeleteEvent evt) {
        TaskData taskData = (TaskData) evt.getData();
        TaskForm taskForm = (TaskForm) evt.getSource();
        int deleteTaskNumber = taskData.getTaskNumber();

        taskChainData = taskChainService.deleteTask(taskChainData, taskData);

        taskFormList.remove(deleteTaskNumber);

        accordion.remove(taskForm);
        refreshTaskFormBindings();
        taskChainDataSessionService.setTaskChainData(taskChainData);
        accordion.open(deleteTaskNumber - 1);

    }

    private void saveTask(TaskForm.SaveEvent evt) {
        TaskData taskData = (TaskData) evt.getData();
        int taskNumber = taskData.getTaskNumber();
        //todo add form for new task

        taskChainData = taskChainService.save(taskChainData, taskNumber);
        taskChainDataSessionService.setTaskChainData(taskChainData);
        refreshTaskFormBindings();
        addNewTask(taskNumber + 1);
//        accordion.open(taskNumber + 1);
    }

    private void refreshTaskFormBindings() {
        for (int i = 0; i < taskFormList.size(); i++){
            taskFormList.get(i).setTask(taskChainData.getTasks().get(i));
        }
    }

}
