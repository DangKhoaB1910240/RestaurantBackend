package com.example.event.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event.cart.Cart;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(@Param("userId") Integer userId);

    List<Reservation> findByStatusAndNgayTaoBefore(Integer status, LocalDateTime time);

}
