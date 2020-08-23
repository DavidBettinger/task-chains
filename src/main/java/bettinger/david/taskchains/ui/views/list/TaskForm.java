package bettinger.david.taskchains.ui.views.list;

import bettinger.david.taskchains.model.data.TaskData;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;

public class TaskForm extends BaseForm<TaskData> {
    TextField name = new TextField("Task name");
    TextArea description = new TextArea("Description");
    DatePicker deadline = new DatePicker("Deadline");


    Binder<TaskData> binder = new Binder<>(TaskData.class);

    public TaskForm() {
        super();
        binder.bind(name, "name");
        binder.bind(description, "description");

        binder.forField(deadline)
                .withConverter(new LocalDateToDateConverter())
                .bind(TaskData::getDeadline, TaskData::setDeadline);

        addListeners();

        add(
                name,
                description,
                deadline,
                createButtonsLayout()
        );
    }

    public String getTaskName(){
        return name.getValue();
    }

    public void setTask(TaskData taskData) {
        binder.setBean(taskData);
    }


    private void addListeners() {

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
    }

    private void validateAndSave() {
        System.out.println(deadline.getValue());
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

}
