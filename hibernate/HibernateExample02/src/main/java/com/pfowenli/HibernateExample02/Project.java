package com.pfowenli.HibernateExample02;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Project {
	private int id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private Set<Employee> employees = new HashSet<Employee>(0);
	
	public Project() {
		super();
	}
	
    public Project(String name, Date beginDate) {
		super();
		this.name = name;
		this.beginDate = beginDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}
