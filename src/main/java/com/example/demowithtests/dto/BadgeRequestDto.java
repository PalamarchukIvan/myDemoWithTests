package com.example.demowithtests.dto;

import lombok.ToString;
@ToString
public class BadgeRequestDto {
    public String firstName;
    public String lastName;
    public String position;
    public Boolean isPrivate = Boolean.FALSE;
}
