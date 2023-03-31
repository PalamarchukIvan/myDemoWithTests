package com.example.demowithtests.dto.BadgeDto;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Badge.State;

public class    BadgeResponseDto {
    public String firstName;
    public String lastName;
    public String position;
    public Badge.State currentState;
    public String key;
}
