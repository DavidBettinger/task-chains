package bettinger.david.chaintask.repository;

import bettinger.david.chaintask.model.ChainTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChainTaskRepository extends JpaRepository<ChainTask, Long> {
    @Query("select c from ChainTask c " +
            "where lower(c.chainName) like lower(concat('%', :searchTerm, '%'))")
    List<ChainTask> search(@Param("searchTerm") String searchTerm);
}
