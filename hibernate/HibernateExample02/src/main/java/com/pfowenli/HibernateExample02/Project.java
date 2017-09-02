package com.pfowenli.HibernateExample02;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Project {
	private int id;
	private String name;
	private Calendar beginDate;
	private Calendar endDate;
	private Set<Employee> employees = new HashSet<Employee>(0);
	
	public Project() {
		super();
	}
	
    public Project(String name, Calendar beginDate) {
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

	public Calendar getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", beginDate=" + (beginDate == null ? null: beginDate.getTime()) + ", endDate=" + (endDate == null? null : endDate.getTime())
				+ ", employees.size()=" + employees.size() + "]";
	}
}
