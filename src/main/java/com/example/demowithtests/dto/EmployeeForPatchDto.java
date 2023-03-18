package com.example.demowithtests.dto;

import com.example.demowithtests.util.anotations.validation.Password;
import com.example.demowithtests.util.anotations.validation.Phone;
import com.example.demowithtests.domain.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
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
    public List<PhotoDto> photos;
    public Gender gender;
    public Boolean isPrivate = Boolean.FALSE;
    @Password
    public String password;
}
