package bettinger.david.taskchains.apiservice;


import bettinger.david.taskchains.model.data.TaskChainData;
import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.service.TaskChainService;
import bettinger.david.taskchains.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mobileApi/v1")
public class TaskChainApiController {
    private final TaskChainService taskChainService;
    private final UserService userService;

    public TaskChainApiController(TaskChainService taskChainService, UserService userService) {
        this.taskChainService = taskChainService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskChainData>> getAll(){
        return new ResponseEntity<>(taskChainService.findAll(), HttpStatus.OK);
    }


    //TODO add Sping Security and correct login
    @GetMapping("/login/{userName}")
    public ResponseEntity<UserData> login(@PathVariable String userName){
        return new ResponseEntity<>(userService.getUserForUsername(userName), HttpStatus.OK);
    }

}
