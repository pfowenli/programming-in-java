package com.spring.many2many.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.many2many.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
