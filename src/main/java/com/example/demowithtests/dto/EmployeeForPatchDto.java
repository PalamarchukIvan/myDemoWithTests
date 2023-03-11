package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class EmployeeForPatchDto {

    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    public String name;
    public String country;
    @Email
    public String email;
    public Set<AddressDto> addresses;
    public Gender gender;
    public Boolean isPrivate = Boolean.FALSE;
}
