package bettinger.david.taskchains.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task extends BaseEntity {

	private String name;

	private String description;

	private String comment;

	private Boolean completed;

	private int taskNumber;

	private Date createdAt;

	private Date deadline;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@PrePersist
	void beforePersist() {
		if (this.completed == null) {
			this.completed = false;
		}

		if (this.createdAt == null) {
			this.createdAt = new Date();
		}

	}
}
