package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    @Query(value = "WITH RECURSIVE badge_ AS (\n" +
            "  SELECT *\n" +
            "  FROM badges b\n WHERE id = :id\n" +
            "  UNION ALL\n" +
            "  SELECT b.*\n" +
            "  FROM badges b\n" +
            "  INNER JOIN badge_ b_ ON b.id = b_.previous_id\n" +
            ") \n" +
            "SELECT * FROM badge_;", nativeQuery = true)
    List<Badge> getBadgeHistory(Integer id);
}
