package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepositoryEMBean implements EmployeeRepositoryEM{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Employee> findEmployeeByPartOfTheName(String letters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root).where(cb.like(root.get("name"), letters));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Employee> findEmployeeByPresentAddress() throws NoSuchMethodException {
        Query annotation = EmployeeRepositoryEM.class.getMethod("findEmployeeByPresentAddress").getAnnotation(Query.class);
        if(annotation == null) return new ArrayList<>();
        return entityManager.createNativeQuery(annotation.value(), Employee.class).getResultList();
    }

}
