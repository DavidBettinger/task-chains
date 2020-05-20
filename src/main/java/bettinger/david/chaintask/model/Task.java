package bettinger.david.chaintask.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private String comment;

	private Boolean completed;

	private int taskNumber;

	private Date createdAt;

	private Date deadline;

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
