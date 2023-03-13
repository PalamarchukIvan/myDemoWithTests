package com.example.demowithtests.dto;

import com.example.demowithtests.anotations.Password;
import com.example.demowithtests.anotations.Phone;
import com.example.demowithtests.domain.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

public class EmployeeForPatchDto {

    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    public String name;
    public String country;
    @Email
    public String email;
    @Phone
    public String phone;
    public Set<AddressDto> addresses;
    public Gender gender;
    public Boolean isPrivate = Boolean.FALSE;
    @Password
    public String password;
}
