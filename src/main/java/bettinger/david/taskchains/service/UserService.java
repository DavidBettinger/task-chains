package bettinger.david.taskchains.service;


import bettinger.david.taskchains.model.converters.UserDataToUser;
import bettinger.david.taskchains.model.converters.UserToUserData;
import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.model.entity.User;
import bettinger.david.taskchains.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserToUserData userConverter;
    private final UserDataToUser userDataConverter;

    public UserService(UserRepository userRepository, UserToUserData userConverter, UserDataToUser userDataConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.userDataConverter = userDataConverter;
    }

    public UserData getUserForUsername(String username){
        User user = userRepository.findByUserName(username).get(0);


        return userConverter.convert(user);
    }

    public void delete(UserData user){
        userRepository.delete(Objects.requireNonNull(userDataConverter.convert(user)));
    }

    public void save(UserData data) {
        userRepository.save(Objects.requireNonNull(userDataConverter.convert(data)));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<UserData> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return findAll();
        } else {
            List<User> users = userRepository.search(filterText);
            sortUsers(users);

            ArrayList<UserData> userDataList = new ArrayList<>();

            users.forEach(user -> userDataList.add(userConverter.convert(user)));

            return userDataList;
        }
    }

    private void sortUsers(List<User> users) {
        users.sort(Comparator.comparing(User::getName));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<UserData> findAll() {

        List<User> users = userRepository.findAll();

        ArrayList<UserData> userDataList = new ArrayList<>();

        users.forEach(taskChain -> userDataList.add(userConverter.convert(taskChain)));

        return userDataList;
    }

}
