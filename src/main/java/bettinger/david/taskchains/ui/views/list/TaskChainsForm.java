package bettinger.david.taskchains.ui.views.list;

import bettinger.david.taskchains.model.data.TaskChainData;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;

public class TaskChainsForm extends BaseForm<TaskChainData> {
    TextField name = new TextField("Task-Chain name");
    TextArea description = new TextArea("Description");
    DatePicker deadline = new DatePicker("Deadline");

    Binder<TaskChainData> binder = new Binder<>(TaskChainData.class);

    public TaskChainsForm() {
        super();
        binder.bind(name, "name");
        binder.bind(description, "description");

        binder.forField(deadline)
                .withConverter(new LocalDateToDateConverter())
                .bind(TaskChainData::getDeadline, TaskChainData::setDeadline);

        addListeners();

        add(
                name,
                description,
                deadline,
                createButtonsLayout()
        );
    }

    public void setChainTask(TaskChainData taskChainData) {
        binder.setBean(taskChainData);
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
