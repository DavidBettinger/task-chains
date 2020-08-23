package bettinger.david.taskchains.service;


import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.UserData;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@VaadinSessionScope
public class TaskChainDataSessionService {

    private TaskChainData taskChainData;

    private UserData userData;

}
