package bettinger.david.chaintask.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "Task_List")
public class ChainTask implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
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

	public int getNextTaskNumber() {
		return this.tasks.size();
	}

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
