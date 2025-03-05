package com.example.event.reservationitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Integer> {
    List<ReservationItem> findByReservationId(Integer reservationId);
}