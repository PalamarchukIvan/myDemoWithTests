package com.example.demowithtests.dto;

import lombok.ToString;

import java.time.Instant;
import java.util.Date;
@ToString
public class PhotoDto {
    public Date uploadDate = Date.from(Instant.now());
    public String description;
    public String cameraType;
    public String photoUrl;
}
