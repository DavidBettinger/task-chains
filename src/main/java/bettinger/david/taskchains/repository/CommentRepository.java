package bettinger.david.taskchains.repository;

import bettinger.david.taskchains.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
