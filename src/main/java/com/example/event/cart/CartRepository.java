package com.example.event.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event.item.Item;
import com.example.event.user.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);

    void deleteByUserId(Integer userId);

    Optional<Cart> findByUserAndItem(User user, Item item);
}