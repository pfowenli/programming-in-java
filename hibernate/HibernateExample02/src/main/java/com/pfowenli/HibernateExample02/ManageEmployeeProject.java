package com.pfowenli.HibernateExample02;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployeeProject {
	
	private static SessionFactory factory;
	
	public static void main(String[] args) {
		
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployeeProject manageEmployeeProject = new ManageEmployeeProject();

		// add employee records
		List<Integer> employeeIDs = new ArrayList<Integer>();
		employeeIDs.add(manageEmployeeProject.addEmployee("Owen", "LI", 38400));
		employeeIDs.add(manageEmployeeProject.addEmployee("Vanessa", "HARRIES", 39200));
		employeeIDs.add(manageEmployeeProject.addEmployee("Emily", "WILSON", 50000));
		
		// add project records
		List<Integer> projectIDs = new ArrayList<Integer>();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"), new Locale("English", "America"));
		calendar.set(2017, Calendar.JULY, 29);
		projectIDs.add(manageEmployeeProject.addProject("Customer Relationship Management", (Calendar) calendar.clone()));
		calendar.set(2017, Calendar.AUGUST, 14);
		projectIDs.add(manageEmployeeProject.addProject("A/B Testing", (Calendar) calendar.clone()));
		calendar.set(2017, Calendar.SEPTEMBER, 6);
		projectIDs.add(manageEmployeeProject.addProject("Customer Behavior Analysis", (Calendar) calendar.clone()));
		
		// add employee-project relations
		manageEmployeeProject.addProjectForEmployee(employeeIDs.get(0), projectIDs.get(0));
		manageEmployeeProject.addProjectForEmployee(employeeIDs.get(0), projectIDs.get(1));
		manageEmployeeProject.addProjectForEmployee(employeeIDs.get(1), projectIDs.get(0));
		manageEmployeeProject.addProjectForEmployee(employeeIDs.get(2), projectIDs.get(0));
		manageEmployeeProject.addProjectForEmployee(employeeIDs.get(2), projectIDs.get(2));

		// list all the employees
		manageEmployeeProject.listEmployees();
		
		// list all the employees
		manageEmployeeProject.listProjects();;
	}

	/*
	 * create an employee
	 */
	public Integer addEmployee(String fname, String lname, int salary) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Integer employeeID = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = new Employee(fname, lname, salary);
			employeeID = (Integer) session.save(employee);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}

	/*
	 * read all the employees
	 */
	public void listEmployees() {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			List employees = session.createQuery("FROM Employee").list();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.println(employee);
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/*
	 * update salary for an employee
	 */
	public void updateSalaryForEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/*
	 * set projects for an employee
	 */
	public void addProjectForEmployee(Integer EmployeeID, Integer projectID) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			Project project = (Project) session.get(Project.class, projectID);
			Set<Project> projects = employee.getProjects();
			projects.add(project);
			employee.setProjects(projects);
			session.update(employee);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* 
	 * delete an employee
	 */
	public void deleteEmployee(Integer employeeID) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, employeeID);
			session.delete(employee);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/*
	 * create an project
	 */
	public Integer addProject(String name, Calendar beginDate) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Integer projectID = null;
		try {
			transaction = session.beginTransaction();
			Project project = new Project(name, beginDate);
			projectID = (Integer) session.save(project);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return projectID;
	}
	
	/*
	 * read all the employees
	 */
	public void listProjects() {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			List projects = session.createQuery("FROM Project").list();
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = projects.iterator(); iterator.hasNext();) {
				Project project = (Project) iterator.next();
				System.out.println(project);
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/* 
	 * delete an project
	 */
	public void deleteProject(Integer projectID) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Project project = (Project) session.get(Project.class, projectID);
			session.delete(project);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}