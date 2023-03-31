package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    @Query(value = "WITH RECURSIVE badge_tree AS (\n" +
            "  SELECT *\n" +
            "  FROM badges b\n WHERE id = :id\n" +
            "  UNION ALL\n" +
            "  SELECT b.*\n" +
            "  FROM badges b\n" +
            "  INNER JOIN badge_tree bt ON b.id = bt.previous_id\n" +
            ") \n" +
            "SELECT * FROM badge_tree;", nativeQuery = true)
    List<Badge> getBadgeHistory(Integer id);
}
