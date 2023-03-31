package com.example.demowithtests.dto.AddressDto;

import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Date;

public class AddressDto {
    public Boolean addressHasActive = Boolean.TRUE;
    public String country;
    public String city;
    public String street;
    public Date date = Date.from(Instant.now());
}
