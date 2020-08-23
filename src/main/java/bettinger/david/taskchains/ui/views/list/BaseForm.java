package bettinger.david.taskchains.ui.views.list;

import bettinger.david.taskchains.model.data.BaseData;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

public abstract class BaseForm<T> extends FormLayout {
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    public BaseForm(){
        addClassName("base-form");
    }

    protected Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

//        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, close);
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class FormEvent extends ComponentEvent<BaseForm> {
        private BaseData baseData;

        protected FormEvent(BaseForm source, BaseData baseData) {
            super(source, false);
            this.baseData = baseData;
        }

        public BaseData getData() {
            return baseData;
        }
    }

    public static class SaveEvent extends FormEvent {
        SaveEvent(BaseForm source, BaseData baseData) {
            super(source, baseData);
        }
    }

    public static class DeleteEvent extends FormEvent {
        DeleteEvent(BaseForm source, BaseData baseData) {
            super(source, baseData);
        }

    }

    public static class CloseEvent extends FormEvent {
        CloseEvent(BaseForm source) {
            super(source, null);
        }
    }
}
