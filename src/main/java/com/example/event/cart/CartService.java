package com.example.event.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event.exception.NotFoundException;
import com.example.event.item.Item;
import com.example.event.item.ItemRepository;
import com.example.event.user.User;
import com.example.event.user.UserRepository;

@Service
public class CartService {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Cart addToCart(CartDto cartDto) {
        User user = userRepository.findById(cartDto.getUser_id())
                .orElseThrow(() -> new NotFoundException("Người dùng không tồn tại"));
        Item item = itemRepository.findById(cartDto.getItem_id())
                .orElseThrow(() -> new NotFoundException("Món ăn không tồn tại"));

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng của user hay chưa
        Optional<Cart> existingCart = this.cartRepository.findByUserAndItem(user, item);

        if (existingCart.isPresent()) {
            // Nếu sản phẩm đã có, cập nhật số lượng
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + cartDto.getQuantity());
            return cartRepository.save(cart);
        } else {
            // Nếu chưa có, tạo mới
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setItem(item);
            cart.setQuantity(cartDto.getQuantity());
            return cartRepository.save(cart);
        }
    }

    public Cart updateCart(Integer id, CartDto cartDto) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Kiểm tra nếu có quantity trong DTO
        if (cartDto.getQuantity() <= 0) {
            // Nếu quantity <= 0 thì xóa luôn
            cartRepository.deleteById(id);
            return null; // Trả về null để thể hiện là giỏ hàng đã bị xóa
        }
        cart.setQuantity(cartDto.getQuantity());

        return cartRepository.save(cart);
    }

    public void removeFromCart(Integer id) {
        cartRepository.deleteById(id);
    }

    public void deleteCartByUserId(Integer userId) {
        cartRepository.deleteByUserId(userId);
    }

    public List<Cart> getCartItems(Integer userId) {
        return cartRepository.findByUserId(userId);
    }
}
