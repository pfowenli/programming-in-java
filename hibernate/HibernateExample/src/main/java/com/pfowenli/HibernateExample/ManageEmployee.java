package com.pfowenli.HibernateExample;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
	
	private static SessionFactory factory;

	public static void main(String[] args) {
		
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		ManageEmployee manageEmployee = new ManageEmployee();

		// add employee records
		List<Integer> employeeIDs = new ArrayList<Integer>();
		employeeIDs.add(manageEmployee.addEmployee("Owen", "LI", 38400));
		employeeIDs.add(manageEmployee.addEmployee("Vanessa", "HARRIES", 39200));
		employeeIDs.add(manageEmployee.addEmployee("Emily", "WILSON", 50000));

		// list all the employees
		System.out.println("before operations");
		manageEmployee.listEmployees();

		// update salary for employee
		manageEmployee.updateSalaryForEmployee(employeeIDs.get(1), 45000);
		manageEmployee.updateSalaryForEmployee(employeeIDs.get(2), 42000);

		// delete employees
		manageEmployee.deleteEmployee(employeeIDs.get(0));
		manageEmployee.deleteEmployee(employeeIDs.get(1));

		// list all the employees
		System.out.println("after operations");
		manageEmployee.listEmployees();
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
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print("  Last Name: " + employee.getLastName());
				System.out.println("  Salary: " + employee.getSalary());
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
}