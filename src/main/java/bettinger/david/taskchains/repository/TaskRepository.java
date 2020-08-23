package bettinger.david.taskchains.repository;

import bettinger.david.taskchains.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TaskRepository extends JpaRepository<Task, Long> {

}
