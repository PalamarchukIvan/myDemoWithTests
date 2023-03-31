package com.example.demowithtests.dto.BadgeDto;

import com.example.demowithtests.domain.Badge;
import com.example.demowithtests.domain.Badge.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import java.util.UUID;

@ToString
public class BadgeRequestDto {
    public String firstName;
    public String lastName;
    public String position;
    @JsonIgnore
    public String key  = UUID.randomUUID().toString();
    public Badge.State currentState = Badge.State.ACTIVE;
    public Boolean isPrivate = Boolean.FALSE;
}
