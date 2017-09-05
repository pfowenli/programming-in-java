package com.spring.many2many.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.many2many.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
