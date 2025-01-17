package com.example.demowithtests.dto.EmployeeDto;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.AddressDto.AddressDto;
import com.example.demowithtests.dto.BadgeDto.BadgeResponseDto;
import com.example.demowithtests.dto.PhotoDto.PhotoReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

@Builder
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
    public Set<AddressDto> addresses;
    public List<PhotoReadDto> photos;
    public BadgeResponseDto badge;
    public Date date;
    public Gender gender;
}
