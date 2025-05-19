package spring.practice.elmenus_lite.bootstrap;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.practice.elmenus_lite.entity.*;
import spring.practice.elmenus_lite.enums.Gender;
import spring.practice.elmenus_lite.repository.*;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty
        if (userTypeRepository.count() == 0) {
            // Add initial data to the database
            UserType adminType = new UserType("Admin");
            adminType.setCreatedBy("admin");
            adminType.setUpdatedBy("admin");

            UserType customer = new UserType("Customer");
            customer.setCreatedBy("admin");
            customer.setUpdatedBy("admin");

            UserType restaurant = new UserType("Restaurant");
            restaurant.setCreatedBy("admin");
            restaurant.setUpdatedBy("admin");

            UserType Delivery = new UserType("Delivery");
            Delivery.setCreatedBy("admin");
            Delivery.setUpdatedBy("admin");
            userTypeRepository.saveAll(List.of(adminType, customer, restaurant, Delivery));

            // Add initial users
            User user1 = User.builder()
                    .firstName("Admin")
                    .lastName("admin")
                    .userType(adminType)
                    .password("admin")
                    .email("admin@admin")
                    .build();
            user1.setCreatedBy("admin");
            user1.setUpdatedBy("admin");

            User user2 = User.builder()
                    .firstName("Ali")
                    .lastName("Ali")
                    .userType(customer)
                    .password("customer")
                    .email("Ali@customer")
                    .build();
            user2.setCreatedBy("admin");
            user2.setUpdatedBy("admin");

            User user3 = User.builder()
                    .firstName("Ahmed")
                    .lastName("Ahmed")
                    .userType(customer)
                    .password("customer")
                    .email("Ahmed@customer")
                    .build();
            user3.setCreatedBy("admin");
            user3.setUpdatedBy("admin");

            User user4 = User.builder()
                    .firstName("Mohamed")
                    .lastName("Mohamed")
                    .userType(customer)
                    .password("customer")
                    .email("Mohamed@customer")
                    .build();
            user4.setCreatedBy("admin");
            user4.setUpdatedBy("admin");

            userRepository.saveAll(List.of(user1, user2, user3, user4));

            // Add initial customers
            Customer customer1 = Customer.builder()
                    .user(user4)
                    .phone("123")
                    .gender(Gender.MALE)
                    .build();
            customer1.setCreatedBy("admin");
            customer1.setUpdatedBy("admin");

            Customer customer2 = Customer.builder()
                    .user(user2)
                    .phone("123")
                    .gender(Gender.MALE)
                    .build();
            customer2.setCreatedBy("admin");
            customer2.setUpdatedBy("admin");

            Customer customer3 = Customer.builder()
                    .user(user3)
                    .phone("123")
                    .gender(Gender.MALE)
                    .build();
            customer3.setCreatedBy("admin");
            customer3.setUpdatedBy("admin");

            customerRepository.saveAll(List.of(customer1, customer2, customer3));

            if (menuRepository.count() == 0) {
                Menu menu1 = Menu.builder()
                        .menuName("Menu1")
                        .build();
                menu1.setCreatedBy("admin");
                menu1.setUpdatedBy("admin");

                Menu menu2 = Menu.builder()
                        .menuName("Menu2")
                        .build();

                menu2.setCreatedBy("admin");
                menu2.setUpdatedBy("admin");

                menuRepository.save(menu1);
                menuRepository.save(menu2);

                MenuItem menuItem1 = new MenuItem(menu1, "Description1", 10.0, true);
                menuItem1.setCreatedBy("admin");
                menuItem1.setUpdatedBy("admin");

                MenuItem menuItem2 = new MenuItem(menu1, "Description2", 20.0, true);
                menuItem2.setCreatedBy("admin");
                menuItem2.setUpdatedBy("admin");

                MenuItem menuItem3 = new MenuItem(menu1, "Description3", 30.0, true);
                menuItem3.setCreatedBy("admin");
                menuItem3.setUpdatedBy("admin");

                MenuItem menuItem4 = new MenuItem(menu2, "Description4", 40.0, true);
                menuItem4.setCreatedBy("admin");
                menuItem4.setUpdatedBy("admin");

                MenuItem menuItem5 = new MenuItem(menu2, "Description5", 50.0, true);
                menuItem5.setCreatedBy("admin");
                menuItem5.setUpdatedBy("admin");

                MenuItem menuItem6 = new MenuItem(menu2, "Description6", 60.0, true);
                menuItem6.setCreatedBy("admin");
                menuItem6.setUpdatedBy("admin");

                menuItemRepository.saveAll(List.of(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6));

                if (cartRepository.count() == 0) {
                    CartItem cartItem1 = new CartItem(null, menuItem1, 2);
                    cartItem1.setCreatedBy("admin");
                    cartItem1.setUpdatedBy("admin");

                    CartItem cartItem2 = new CartItem(null, menuItem2, 3);
                    cartItem2.setCreatedBy("admin");
                    cartItem2.setUpdatedBy("admin");

                    CartItem cartItem3 = new CartItem(null, menuItem3, 4);
                    cartItem3.setCreatedBy("admin");
                    cartItem3.setUpdatedBy("admin");

                    CartItem cartItem4 = new CartItem(null, menuItem4, 5);
                    cartItem4.setCreatedBy("admin");
                    cartItem4.setUpdatedBy("admin");

                    CartItem cartItem5 = new CartItem(null, menuItem5, 6);
                    cartItem5.setCreatedBy("admin");
                    cartItem5.setUpdatedBy("admin");

                    CartItem cartItem6 = new CartItem(null, menuItem6, 7);
                    cartItem6.setCreatedBy("admin");
                    cartItem6.setUpdatedBy("admin");

                    Cart cart = Cart.builder()
                            .customer(customer1)
                            .cartItems(List.of(cartItem1, cartItem2, cartItem4))
                            .build();
                    cart.setCreatedBy("admin");
                    cart.setUpdatedBy("admin");

                    Cart cart2 = Cart.builder()
                            .customer(customer2)
                            .cartItems(List.of(cartItem3))
                            .build();
                    cart2.setCreatedBy("admin");
                    cart2.setUpdatedBy("admin");

                    Cart cart3 = Cart.builder()
                            .customer(customer3)
                            .cartItems(List.of(cartItem5, cartItem6))
                            .build();
                    cart3.setCreatedBy("admin");
                    cart3.setUpdatedBy("admin");

                    cartRepository.saveAll(List.of(cart, cart2, cart3));
                    cartItem1.setCart(cart);
                    cartItem2.setCart(cart);
                    cartItem3.setCart(cart2);
                    cartItem4.setCart(cart);
                    cartItem5.setCart(cart3);
                    cartItem6.setCart(cart3);
                    cartItemRepository.saveAll(List.of(cartItem1, cartItem2, cartItem3, cartItem4, cartItem5, cartItem6));
                }

            }

        }

    }
}
