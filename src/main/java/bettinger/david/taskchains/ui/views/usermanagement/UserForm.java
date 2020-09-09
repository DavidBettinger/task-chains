package bettinger.david.taskchains.ui.views.usermanagement;

import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.ui.views.BaseForm;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class UserForm extends BaseForm<UserData> {
    TextField name = new TextField("Name");
    TextField firstName = new TextField("First Name");
    TextField eMail = new TextField("Email / Username");
    //TODO add role and Group selection
    //TODO add password TextField

    Binder<UserData> binder = new Binder<>(UserData.class);

    public UserForm() {
        super();
        binder.bind(name, "name");
        binder.bind(firstName, "firstName");
        binder.bind(eMail, "userName");

        addListeners();

        add(
                name,
                firstName,
                eMail,
                createButtonsLayout()
        );
    }

    public void setUser(UserData userData) {
        binder.setBean(userData);
    }


    private void addListeners() {

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

}
