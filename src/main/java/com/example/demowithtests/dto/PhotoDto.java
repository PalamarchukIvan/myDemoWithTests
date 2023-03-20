package com.example.demowithtests.dto;

import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
@ToString
public class PhotoDto {
    public LocalDate uploadDate = LocalDate.now();
    public String path;
    public String name;
    public String format;
}
