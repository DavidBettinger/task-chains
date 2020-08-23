package bettinger.david.taskchains.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskChain extends BaseEntity {

	private Date createdAt;

	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;

	private String name;

	private String description;

	private Boolean completed;

	@OneToOne
	private User createdBy;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Task> tasks = new ArrayList<>();


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
