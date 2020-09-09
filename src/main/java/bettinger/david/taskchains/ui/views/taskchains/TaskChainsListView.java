package bettinger.david.taskchains.ui.views.taskchains;

import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.service.TaskChainDataSessionService;
import bettinger.david.taskchains.service.TaskChainService;
import bettinger.david.taskchains.ui.MainLayout;
import bettinger.david.taskchains.ui.views.tasks.TasksAccordionView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Date;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Admin | Chain-Tasks")
public class TaskChainsListView extends VerticalLayout {
    private final TaskChainsForm form;

    Grid<TaskChainData> grid = new Grid<>(TaskChainData.class);
    TextField filterText = new TextField();

    private final TaskChainService taskChainService;
    private final TaskChainDataSessionService taskChainDataSessionService;

    public TaskChainsListView(TaskChainService taskChainService, TaskChainDataSessionService taskChainDataSessionService) {
        this.taskChainService = taskChainService;
        this.taskChainDataSessionService = taskChainDataSessionService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new TaskChainsForm();
        form.addListener(TaskChainsForm.SaveEvent.class, this::saveTaskChain);
        form.addListener(TaskChainsForm.DeleteEvent.class, this::deleteTaskChain);
        form.addListener(TaskChainsForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteTaskChain(TaskChainsForm.DeleteEvent evt) {
        taskChainService.delete((TaskChainData) evt.getData());
        updateList();
        closeEditor();
    }

    private void saveTaskChain(TaskChainsForm.SaveEvent evt) {
        taskChainService.save((TaskChainData) evt.getData());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addChainTaskButton = new Button("Add task-chain", click -> addTaskChainData());
        Button addTasksButton = new Button("Add tasks to a chain", click -> addTasks());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addChainTaskButton, addTasksButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addTasks() {
        if (taskChainService.count() > 0) {
//            Notification.show("Count = " + taskChainsService.count(), 1000, Notification.Position.MIDDLE);
            TaskChainData taskChainData = grid.asSingleSelect().getValue();
            if(taskChainData != null){
                taskChainDataSessionService.setTaskChainData(taskChainData);
                getUI().ifPresent(ui -> ui.navigate(TasksAccordionView.class));
            } else {
                Notification.show("Please select the task-chain you want to add tasks to.", 1000, Notification.Position.MIDDLE);
            }
        } else {
            Notification.show("Please create a task-chain first.", 1000, Notification.Position.MIDDLE);
        }
    }

    private void addTaskChainData() {
        grid.asSingleSelect().clear();
        TaskChainData taskChainData = new TaskChainData();
        taskChainData.setDeadline(new Date());
        editChainTask(taskChainData);
    }

    private void configureGrid() {
        grid.addClassName("task-chains-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("description");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("tasks");


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editChainTask(evt.getValue()));
    }

    private void editChainTask(TaskChainData taskChainData) {
        if (taskChainData == null) {
            closeEditor();
        } else {
            form.setChainTask(taskChainData);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setChainTask(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(taskChainService.findAll(filterText.getValue()));
    }

}
