package bettinger.david.taskchains.repository;

import bettinger.david.taskchains.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserName(String userName);

}
