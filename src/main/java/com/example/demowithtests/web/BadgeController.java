package com.example.demowithtests.web;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.dto.BadgeRequestDto;
import com.example.demowithtests.dto.BadgeResponseDto;
import com.example.demowithtests.service.BadgeService;
import com.example.demowithtests.util.config.MapStruct.BadgeMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class BadgeController {
    private final BadgeService badgeService;

    @PostMapping("/badges")
    @ResponseStatus(HttpStatus.OK)
    public BadgeResponseDto create(@RequestBody BadgeRequestDto badgeRequestDto) {
        System.err.println(BadgeMapper.INSTANCE.badgeRequestDtoToBadge(badgeRequestDto));
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(
                badgeService.create(BadgeMapper.INSTANCE.badgeRequestDtoToBadge(badgeRequestDto)));
    }

    @GetMapping("/badges/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BadgeResponseDto readById(@PathVariable Integer id) {
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(badgeService.getById(id));
    }
    @PutMapping("/badges/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BadgeResponseDto updateById(@PathVariable Integer id, @RequestBody BadgeRequestDto badgeRequestDto) {
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(
                badgeService.updateBudge(id, BadgeMapper.INSTANCE.badgeRequestDtoToBadge(badgeRequestDto)));
    }
    @GetMapping("/badges")
    @ResponseStatus(HttpStatus.OK)
    public List<BadgeResponseDto> getAll() {
        List<Badge> badges = badgeService.getAllBadges();
        System.err.println(badges);
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(badgeService.getAllBadges());
    }
    @DeleteMapping("/badges/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeById (@PathVariable Integer id) {
        badgeService.deleteBudge(id);
    }
}
