package com.spring.many2many;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.many2many.model.Employee;
import com.spring.many2many.model.Project;
import com.spring.many2many.repository.EmployeeRepository;
import com.spring.many2many.repository.ProjectRepository;

@SpringBootApplication
public class SpringMany2ManyApplication implements CommandLineRunner {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMany2ManyApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... arg0) throws Exception {
		
		// set up employee objects
		Employee employee1 = new Employee("Owen", "LI", 48000);
		Employee employee2 = new Employee("Vanessa", "Hsieh", 52000);
		Employee employee3 = new Employee("Emily", "Wu", 60000);
		
		// set up project objects
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, Calendar.JULY, 24);
		Project project1 = new Project("A/B Testing", calendar);
		calendar = Calendar.getInstance();
		calendar.set(2017, Calendar.AUGUST, 7);
		Project project2 = new Project("Customer Relationship Managements", calendar);
		
		// set projects for employee1
		Set<Project> projectSet = new HashSet<Project>();
		projectSet.add(project1);
		projectSet.add(project2);
		
		employee1.setProjects(projectSet);
		
		// set projects for employee2
		projectSet = new HashSet<Project>();
		projectSet.add(project1);
		
		employee2.setProjects(projectSet);
		
		// set projects for employee3
		projectSet = new HashSet<Project>();
		projectSet.add(project2);
		
		employee3.setProjects(projectSet);
		
		// save employee objects
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		
		// set employees for project1
		Set<Employee> employeeSet = new HashSet<Employee>();
		employeeSet.add(employee1);
		employeeSet.add(employee2);
		
		project1.setEmployees(employeeSet);
		
		// set employees for project2
		employeeSet = new HashSet<Employee>();
		employeeSet.add(employee1);
		employeeSet.add(employee3);
		
		project2.setEmployees(employeeSet);
		
		// save project objects
		projectRepository.save(project1);
		projectRepository.save(project2);
		
		List<Employee> employeeList = employeeRepository.findAll();
		System.out.println("===================Employees==================");
		System.out.println("number of employees: " + employeeList.size());
		for (Employee employee : employeeList) {
		    System.out.println(employee.toString());
		}
		
		System.out.println("===================Projects==================");
		List<Project> projectList = projectRepository.findAll();
		System.out.println("number of projects: " + projectList.size());
		for (Project project : projectList) {
			System.out.println(project.toString());
		}
	}
}
