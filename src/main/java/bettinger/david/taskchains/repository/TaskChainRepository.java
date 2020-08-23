package bettinger.david.taskchains.repository;

import bettinger.david.taskchains.model.entity.TaskChain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskChainRepository extends JpaRepository<TaskChain, Long> {
    @Query("select c from TaskChain c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<TaskChain> search(@Param("searchTerm") String searchTerm);
}
