package bettinger.david.taskchains.ui.views.usermanagement;

import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.service.UserService;
import bettinger.david.taskchains.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user-management", layout = MainLayout.class)
@PageTitle("Admin | User management")
public class UserListView extends VerticalLayout {
    private final UserForm form;

    Grid<UserData> grid = new Grid<>(UserData.class);
    TextField filterText = new TextField();

    private final UserService userService;


    public UserListView(UserService userService) {
        this.userService = userService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new UserForm();
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent evt) {
        userService.delete((UserData) evt.getData());
        updateList();
        closeEditor();
    }

    private void saveUser(UserForm.SaveEvent evt) {
        userService.save((UserData) evt.getData());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Add User", click -> addUserData());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addUserData() {
        grid.asSingleSelect().clear();
        UserData userData = new UserData();
        editUser(userData);
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editUser(evt.getValue()));
    }

    private void editUser(UserData userData) {
        if (userData == null) {
            closeEditor();
        } else {
            form.setUser(userData);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(userService.findAll(filterText.getValue()));
    }

}
