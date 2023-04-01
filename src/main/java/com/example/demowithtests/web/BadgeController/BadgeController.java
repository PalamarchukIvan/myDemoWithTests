package com.example.demowithtests.web.BadgeController;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.dto.BadgeDto.BadgeRequestDto;
import com.example.demowithtests.dto.BadgeDto.BadgeResponseDto;

import java.util.List;

public interface BadgeController {

    BadgeResponseDto create(BadgeRequestDto badgeRequestDto);

    BadgeResponseDto readById(Integer id);

    BadgeResponseDto updateById(Integer id, BadgeRequestDto badgeRequestDto);

    List<BadgeResponseDto> getAll();

    List<BadgeResponseDto> getBadgeHierarchy(Integer id);

    void removeById(Integer id, Badge.State reason);
}
