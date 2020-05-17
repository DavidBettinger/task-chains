package bettinger.david.chaintask.repository;

import bettinger.david.chaintask.model.Task;
import org.springframework.data.repository.CrudRepository;



public interface TaskRepository extends CrudRepository<Task, Long> {

}
