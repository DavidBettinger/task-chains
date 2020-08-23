package bettinger.david.taskchains.model.converters;
import bettinger.david.taskchains.model.data.CommentData;
import bettinger.david.taskchains.model.entity.Comment;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentData implements  Converter<Comment, CommentData> {

    private final UserToUserData userConverter;

    public CommentToCommentData(UserToUserData userConverter) {
        this.userConverter = userConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CommentData convert(Comment comment) {
        CommentData commentData = new CommentData();
        commentData.setCreatedAt(comment.getCreatedAt());
        commentData.setCreatedBy(userConverter.convert(comment.getCreatedBy()));
        commentData.setText(comment.getText());
        commentData.setId(comment.getId());

        return commentData;
    }
}
