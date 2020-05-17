package bettinger.david.chaintask.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;

	@PrePersist
	void beforPersist() {
		if (this.completed == null) {
			this.completed = false;
		}

		if (this.createdAt == null) {
			this.createdAt = new Date();
		}

	}
}
