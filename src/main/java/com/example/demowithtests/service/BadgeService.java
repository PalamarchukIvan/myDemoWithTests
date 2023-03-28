package com.example.demowithtests.service;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.repository.BadgeRepository;

import java.util.List;
import java.util.Stack;

public interface BadgeService {
    Badge getById(Integer id);
    Badge create(Badge badge);
    List<Badge> getAllBadges();
    void deleteBudge(Integer id);
    Badge updateBudge(Integer id, Badge updateBadge);
}
