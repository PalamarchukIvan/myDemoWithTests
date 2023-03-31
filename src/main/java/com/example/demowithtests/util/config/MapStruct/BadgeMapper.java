package com.example.demowithtests.util.config.MapStruct;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.dto.BadgeDto.BadgeRequestDto;
import com.example.demowithtests.dto.BadgeDto.BadgeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BadgeMapper {
    BadgeMapper INSTANCE = Mappers.getMapper(BadgeMapper.class);
    BadgeResponseDto badgeToBadgeResponseDto(Badge badge);
    Badge badgeRequestDtoToBadge(BadgeRequestDto badgeRequestDto);

    List<BadgeResponseDto> badgeToBadgeResponseDto(List<Badge> badges);
}
