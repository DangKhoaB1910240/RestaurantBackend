package com.example.event.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/cart")
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartDto cartDto) {
        Cart cart = cartService.addToCart(cartDto);
        return ResponseEntity.ok(cart);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Integer id, @RequestBody CartDto cartDto) {
        Cart updatedCart = cartService.updateCart(id, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Integer id) {
        cartService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/user/{id}")
    public ResponseEntity<Void> deleteCartByUserId(@PathVariable Integer id) {
        cartService.deleteCartByUserId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Integer userId) {
        List<Cart> carts = cartService.getCartItems(userId);
        return ResponseEntity.ok(carts);
    }
}
