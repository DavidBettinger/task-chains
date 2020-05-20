package bettinger.david.chaintask.repository;

import bettinger.david.chaintask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TaskRepository extends JpaRepository<Task, Long> {

}
