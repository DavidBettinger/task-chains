package bettinger.david.taskchains.service;


import bettinger.david.taskchains.model.converters.UserToUserData;
import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.model.entity.User;
import bettinger.david.taskchains.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserToUserData userConverter;

    public UserService(UserRepository userRepository, UserToUserData userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserData getUserForUsername(String username){
        User user = userRepository.findByUserName(username).get(0);


        return userConverter.convert(user);
    }
}
