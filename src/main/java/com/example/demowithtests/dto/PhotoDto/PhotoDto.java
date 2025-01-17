package com.example.demowithtests.dto.PhotoDto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PhotoDto {
    public LocalDate uploadDate = LocalDate.now();
    public String name;
    public String format;
    public String url;
    public Boolean isPrivate = Boolean.FALSE;
}
