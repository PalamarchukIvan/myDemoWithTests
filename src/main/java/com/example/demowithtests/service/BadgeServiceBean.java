package com.example.demowithtests.service;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.repository.BadgeRepository;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BadgeServiceBean implements BadgeService {
    private final BadgeRepository badgeRepository;

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
        return badgeRepository.findAll().stream().filter(badge -> !badge.getIsPrivate()).collect(Collectors.toList());
    }

    @Override
    public void deleteBudge(Integer id) {
        Badge badge = badgeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        badge.setIsPrivate(Boolean.TRUE);
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
}
