package com.example.demowithtests.repository;

import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployee;
import com.example.demowithtests.domain.ProjectsToEmployees.ProjectEmployeeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, ProjectEmployeeKey> {

}
