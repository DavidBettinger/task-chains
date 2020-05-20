package bettinger.david.chaintask.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Task_List")
public class ChainTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date createdAt;

	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;

	private String chainName;

	private String description;

	private int size;

	private Boolean completed;

	@ManyToMany(targetEntity = Task.class)
	private List<Task> tasks = new ArrayList<>();

	public void addTask(Task task) {
		this.tasks.add(task);
		this.size = this.tasks.size();
	}


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
