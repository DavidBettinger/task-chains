package bettinger.david.chaintask.ui.views.list;

import bettinger.david.chaintask.model.ChainTask;
import bettinger.david.chaintask.service.ChainTaskService;
import bettinger.david.chaintask.service.TaskService;
import bettinger.david.chaintask.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Date;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Admin | Chain-Tasks")
public class ListView extends VerticalLayout {
    private final ChainTaskForm form;
    Grid<ChainTask> grid = new Grid<>(ChainTask.class);
    TextField filterText = new TextField();

    private ChainTaskService chainTaskService;

    public ListView(ChainTaskService chainTaskService,
                    TaskService taskService) {
        this.chainTaskService = chainTaskService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new ChainTaskForm();
        form.addListener(ChainTaskForm.SaveEvent.class, this::saveContact);
        form.addListener(ChainTaskForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ChainTaskForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteContact(ChainTaskForm.DeleteEvent evt) {
        chainTaskService.delete(evt.getChainTask());
        updateList();
        closeEditor();
    }

    private void saveContact(ChainTaskForm.SaveEvent evt) {
        chainTaskService.save(evt.getChainTask());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addChainTaskButton = new Button("Add chain-task", click -> addChainTask());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addChainTaskButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addChainTask() {
        grid.asSingleSelect().clear();
        editChainTask(new ChainTask());
    }

    private void configureGrid() {
        grid.addClassName("chain-task-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("description");
        grid.removeColumnByKey("tasks");
     //   grid.setColumns("firstName", "lastName", "email", "status");


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editChainTask(evt.getValue()));
    }

    private void editChainTask(ChainTask chainTask) {
        if (chainTask == null) {
            closeEditor();
        } else {
            chainTask.setDeadline(new Date());
            form.setChainTask(chainTask);
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
        grid.setItems(chainTaskService.findAll(filterText.getValue()));
    }

}
