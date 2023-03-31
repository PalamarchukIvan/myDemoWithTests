package com.example.demowithtests.service;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.BadgeRepository;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BadgeServiceBean implements BadgeService {
    private final BadgeRepository badgeRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Badge getById(Integer id) {
        Badge badge = badgeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (badge.getIsPrivate()) throw new ResourceIsPrivateException();
        return badge;
    }

    @Override
    public Badge create(Badge badge) {
        return badgeRepository.save(badge);
    }

    @Override
    public List<Badge> getAllBadges() {
        System.err.println(badgeRepository.findAll().stream().map(Badge::getEmployee).collect(Collectors.toList()));
        return badgeRepository.findAll().stream().filter(badge -> !badge.getIsPrivate()).collect(Collectors.toList());
    }

    @Override
    public void deleteBudge(Integer id) {
        Badge badge = badgeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        badge.setIsPrivate(Boolean.TRUE);
        if (badge.getEmployee() != null) {
            Employee formerEmployee = badge.getEmployee();
            formerEmployee.setBadge(null);
            employeeRepository.save(formerEmployee);
        }
        badgeRepository.save(badge);
    }

    @Override
    public Badge updateBudge(Integer id, Badge updateBadge) {
        return badgeRepository.findById(id)
                .map(badge -> {
                    if (badge.getIsPrivate()) throw new ResourceIsPrivateException();

                    badge.setFirstName(updateBadge.getFirstName());
                    badge.setLastName(updateBadge.getLastName());
                    badge.setPosition(updateBadge.getPosition());
                    return badgeRepository.save(badge);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Badge updateBadgeToEmployee(Badge badge) {
        if (badge.getEmployee() == null) return badge;
        System.err.println("  ");
        badge.setFirstName(badge.getEmployee().getName().split(" ")[0]);
        badge.setLastName(badge.getEmployee().getName().split(" ")[1]);
        return badgeRepository.save(badge);
    }

    @Override
    public Badge inheriteBadge(Badge badge) {
        Badge newBadge = Badge.builder()
                .previousBadge(badge)
                .employee(badge.getEmployee())
                .lastName(badge.getLastName())
                .firstName(badge.getFirstName())
                .position(badge.getPosition())
                .isPrivate(badge.getIsPrivate())
                .build();
        badge.setIsPrivate(Boolean.TRUE);
        badgeRepository.save(badge);
        return badgeRepository.save(newBadge);
    }

    @Override
    public List<Badge> showBadgeTree(Integer id) {
        return badgeRepository.getBadgeTree(id);
    }
}
