package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

public class EmployeeReadDto {

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;
    public String country;
    @Email
    @NotNull
    public String email;
    public String phone;
    public Set<AddressDto> addresses = new HashSet<>();
    public List<PhotoReadDto> photos;
    public BadgeResponseDto badge;
    public Date date = Date.from(Instant.now());
    public Gender gender;
}
