package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class EmployeeRepositoryEMBean implements EmployeeRepositoryEM{
    private EntityManagerFactory entityManagerFactory;
    @Override
    public List<Employee> findEmployeeByPartOfTheName(String letters) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root).where(cb.like(root.get("name"), "%" + letters + "%"));
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public List<Employee> findEmployeeByPresentAddress() throws NoSuchMethodException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query annotation = EmployeeRepositoryEM.class.getMethod("findEmployeeByPresentAddress").getAnnotation(Query.class);
        if(annotation == null) return new ArrayList<>();
        return entityManager.createNativeQuery(annotation.value(), Employee.class).getResultList();
    }

}
