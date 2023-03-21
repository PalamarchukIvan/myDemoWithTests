package com.example.demowithtests.dto;

import lombok.ToString;

import java.time.LocalDate;

@ToString
public class PhotoReadDto {
    public LocalDate uploadDate;
    public String url;
}
