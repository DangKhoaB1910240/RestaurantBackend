package com.example.event.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event.cart.Cart;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(@Param("userId") Integer userId);

    List<Reservation> findByStatusAndNgayTaoBefore(Integer status, LocalDateTime time);

    @Query("SELECT r FROM Reservation r WHERE (:userFullname IS NULL OR LOWER(r.user.fullname) LIKE LOWER(CONCAT('%', :userFullname, '%')) OR LOWER(r.user.username) LIKE LOWER(CONCAT('%', :userFullname, '%')))")
    List<Reservation> findByUserFullnameOrUsername(@Param("userFullname") String userFullname);

}
