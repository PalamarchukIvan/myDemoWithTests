package com.example.demowithtests.service;

import com.example.demowithtests.domain.Badge;

import java.util.List;

public interface BadgeService {
    Badge getById(Integer id);
    Badge create(Badge badge);
    List<Badge> getAllBadges();
    void deleteBudge(Integer id);
    Badge updateBudge(Integer id, Badge updateBadge);
    Badge updateBadgeToEmployee(Badge badge);
}
