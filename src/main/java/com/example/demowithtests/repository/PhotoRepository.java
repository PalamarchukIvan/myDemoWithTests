package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    @Query(nativeQuery = true, value = "select max(photo.id) from photo")
    Integer findLastPhotoId();
}
