package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // return all items contained in this cart
    List<CartItem> findByCartCartId(Long cartId);

    // return the price and availability for cart item
    List<CartItem> findByMenuItemMenuItemId(Long menuItemId);
}
