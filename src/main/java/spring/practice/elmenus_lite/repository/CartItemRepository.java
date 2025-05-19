package spring.practice.elmenus_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.practice.elmenus_lite.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartCartId(Long cartId);

    List<CartItem> findByMenuItemMenuItemId(Long menuItemId);

    Optional<CartItem> findByCartItemIdAndCartCartId(Long cartItemId, Long cartId);
}
