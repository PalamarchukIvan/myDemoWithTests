package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Employee;
import lombok.ToString;

import javax.persistence.OneToOne;
@ToString
public class BadgeRequestDto {
    public String firstName;
    public String lastName;
    public String position;
    public Boolean isPrivate = Boolean.FALSE;
}
