package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.model.entity.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserDataToUser implements Converter<UserData, User> {

    @Synchronized
    @Nullable
    @Override
    public User convert(UserData userData) {
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setName(userData.getName());
        user.setRole(userData.getRole());
        user.setId(userData.getId());
        user.setUserName(userData.getUserName());

        //TODO How to handle the password

        return user;
    }
}
