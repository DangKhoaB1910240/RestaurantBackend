package com.example.event.item;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item> findByCategoryId(Integer organizerId);
    List<Item> findTop5ByCategoryIdAndIdNot(Integer organizerId, Integer eventId);
    Boolean existsByCategoryId(Integer organizerId);
    List<Item> findAll(Specification<Item> spec);
}
