package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.UserData;
import bettinger.david.taskchains.model.entity.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserData implements Converter<User, UserData> {

    @Synchronized
    @Nullable
    @Override
    public UserData convert(User user) {
        UserData userData = new UserData();
        userData.setFirstName(user.getFirstName());
        userData.setName(user.getName());
        userData.setRole(user.getRole());
        userData.setId(user.getId());
        userData.setUserName(user.getUserName());

        return userData;
    }
}
