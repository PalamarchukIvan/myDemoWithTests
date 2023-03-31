package com.example.demowithtests.web.BadgeController;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Badge.State;
import com.example.demowithtests.dto.BadgeDto.BadgeRequestDto;
import com.example.demowithtests.dto.BadgeDto.BadgeResponseDto;
import com.example.demowithtests.service.BadgeService.BadgeService;
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
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(badgeService.getAllBadges());
    }
    @GetMapping("/badges/tree/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BadgeResponseDto> getBadgeHierarchy(@PathVariable Integer id){
        System.err.println(badgeService.showBadgeTree(id));
        return BadgeMapper.INSTANCE.badgeToBadgeResponseDto(badgeService.showBadgeTree(id));
    }
    @DeleteMapping("/badges/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeById (@PathVariable Integer id) {
        badgeService.deleteBudge(id);
    }

    @DeleteMapping("/badges/withReason/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeById (@PathVariable Integer id, @RequestParam(required = false, name = "reason", defaultValue = "ANOTHER") Badge.State reason) {
        badgeService.deleteBudge(id, reason);
    }
}
