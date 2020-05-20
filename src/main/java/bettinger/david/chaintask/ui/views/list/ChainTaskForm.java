package bettinger.david.chaintask.ui.views.list;

import bettinger.david.chaintask.model.ChainTask;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ChainTaskForm extends FormLayout {
    TextField chainName = new TextField("Chain-Task name");
    TextArea description = new TextArea("Description");
    DatePicker deadline = new DatePicker("Deadline");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ChainTask> binder = new Binder<>(ChainTask.class);

    public ChainTaskForm() {
        addClassName("chain-task-form");
        binder.bind(chainName, "chainName");
        binder.bind(description, "description");
//        status.setItems(Contact.Status.values());
//        company.setItems(companies);
//        company.setItemLabelGenerator(Company::getName);

        binder.forField(deadline)
                .withConverter(new LocalDateToDateConverter())
                .bind(ChainTask::getDeadline, ChainTask::setDeadline);

        add(
                chainName,
                description,
                deadline,
                createButtonsLayout()
        );
    }

    private LocalDate toLocalDate(Date date){
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public void setChainTask(ChainTask chainTask) {
        binder.setBean(chainTask);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

//        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        System.out.println(deadline.getValue());
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class ChainTaskFormFormEvent extends ComponentEvent<ChainTaskForm> {
        private ChainTask chainTask;

        protected ChainTaskFormFormEvent(ChainTaskForm source, ChainTask chainTask) {
            super(source, false);
            this.chainTask = chainTask;
        }

        public ChainTask getChainTask() {
            return chainTask;
        }
    }

    public static class SaveEvent extends ChainTaskFormFormEvent {
        SaveEvent(ChainTaskForm source, ChainTask chainTask) {
            super(source, chainTask);
        }
    }

    public static class DeleteEvent extends ChainTaskFormFormEvent {
        DeleteEvent(ChainTaskForm source, ChainTask chainTask) {
            super(source, chainTask);
        }

    }

    public static class CloseEvent extends ChainTaskFormFormEvent {
        CloseEvent(ChainTaskForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
