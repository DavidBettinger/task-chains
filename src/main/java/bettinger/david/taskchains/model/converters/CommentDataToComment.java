package bettinger.david.taskchains.model.converters;

import bettinger.david.taskchains.model.data.CommentData;
import bettinger.david.taskchains.model.entity.Comment;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommentDataToComment implements Converter<CommentData, Comment> {

    private final UserDataToUser userDataConverter;

    public CommentDataToComment(UserDataToUser userDataConverter) {
        this.userDataConverter = userDataConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Comment convert(CommentData commentData) {
        Comment comment = new Comment();
        comment.setCreatedAt(commentData.getCreatedAt());
        comment.setCreatedBy(userDataConverter.convert(commentData.getCreatedBy()));
        comment.setText(commentData.getText());
        comment.setId(commentData.getId());

        return comment;
    }
}
