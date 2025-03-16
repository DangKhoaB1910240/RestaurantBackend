package com.example.event.reservation;

import java.time.LocalDateTime;
import java.util.List;

import com.example.event.item.Item;
import com.example.event.reservationitem.ReservationItem;
import com.example.event.table.Table;
import com.example.event.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // Người đặt

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = true)
    private Table table; // Bàn được đặt

    private LocalDateTime reservationTime; // Ngày giờ đặt bàn
    private Long depositFee; // Tiền cọc

    private Integer status; // Trạng thái đặt bàn 0: Chưa xử lý, 1: Đã cọc, 2: Đã nhận bàn, 3: Đã hủy bỏ

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReservationItem> items; // Danh sách món ăn đi kèm nếu có
    private LocalDateTime ngayTao; // Ngày tạo, mặc định là ngày giờ hiện tại
    private Integer orderType;
    private Long totalAmount = 0L;

    @PrePersist
    protected void onCreate() {
        this.ngayTao = LocalDateTime.now();
    }
}
