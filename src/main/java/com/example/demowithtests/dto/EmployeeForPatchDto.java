package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import java.util.HashSet;
import java.util.Set;

public class EmployeeForPatchDto {
    public Integer id;
    public String name;
    public String country;
    public String email;
    public Set<AddressDto> addresses = new HashSet<>();
    public Gender gender;
    public Boolean isPrivate = Boolean.FALSE;
}
