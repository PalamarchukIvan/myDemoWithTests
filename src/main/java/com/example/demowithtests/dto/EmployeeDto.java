package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.util.anotations.validation.Password;
import com.example.demowithtests.util.anotations.validation.Phone;
import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@ToString
public class EmployeeDto {

    public Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;
    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;
    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;
    @Phone(from = Phone.Country.UKRAINE)
    public String phone;

    public Set<AddressDto> addresses = new HashSet<>();
    @NotNull(message = "Photos may not be null")
    public List<PhotoDto> photos;
    public Gender gender;
    public Boolean isPrivate = Boolean.FALSE;
    @Password
    public String password;
}
