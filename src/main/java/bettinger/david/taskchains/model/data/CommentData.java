package bettinger.david.taskchains.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentData {

    private String id;

    private String text;

    private UserData createdBy;

    private Date createdAt;

}
