package sk.ppmscan.app.importexport.ignoredmanagers.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "ignored_managers", indexes = {
		@Index(columnList = "manager_id", name = "idx_ignored_managers", unique = true) })
public class IgnoredManager {

	@Id
	@Column(name = "manager_id", nullable = false, unique = true)
	private Long managerId;

	public IgnoredManager() {
		super();
	}

	public IgnoredManager(Long managerId) {
		super();
		this.managerId = managerId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

}
