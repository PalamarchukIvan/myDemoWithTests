package com.example.demowithtests.service.BadgeService;

import com.example.demowithtests.domain.Badge;

import java.util.List;

public interface BadgeService {
    Badge getById(Integer id);
    Badge create(Badge badge);
    List<Badge> getAllBadges();
    void deleteBudge(Integer id, Badge.State reason);
    Badge updateBudge(Integer id, Badge updateBadge);
    Badge updateBadgeToEmployee(Badge badge);
    Badge inheriteBadge(Badge badge, Badge.State reason);
    List<Badge> showBadgeHistory(Integer id);
}
