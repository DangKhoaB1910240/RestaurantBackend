package com.example.event.ghe;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GheRepository extends JpaRepository<Ghe, Integer> {
    Ghe findByLoaiGhe(@Param("loaiGhe") Integer loaiGhe);
}
