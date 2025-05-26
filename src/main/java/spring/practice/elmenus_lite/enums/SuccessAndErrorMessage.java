package spring.practice.elmenus_lite.enums;

import lombok.Getter;

@Getter
public enum SuccessAndErrorMessage {
    // info
    INFO("Info"),

    // Success Messages
    SUCCESS("Success"),
    QUANTITY_UPDATED_SUCCESSFULLY("Quantity updated successfully"),
    CART_ITEM_ADDED_SUCCESSFULLY("Cart item added successfully"),
    CART_ITEM_DELETED_SUCCESSFULLY("Cart item deleted successfully"),
    CART_CLEARED_SUCCESSFULLY("Cart cleared successfully"),
    CART_RETRIEVED_SUCCESSFULLY("Cart retrieved successfully"),

    // Error Messages
    ERROR("Error"),
    INVALID_INPUT("Invalid input"),
    INVALID_QUANTITY("Invalid quantity"),
    INVALID_CART_ITEM_ID("Invalid cart item ID"),
    INVALID_CART_ID("Invalid cart ID"),
    INVALID_CUSTOMER_ID("Invalid customer ID"),
    CAN_NOT_UPDATE_QUANTITY("can not update quantity"),
    CAN_NOT_DELETE_CART_ITEM("Can not delete cart item"),
    CAN_NOT_ADD_CART_ITEM("Can not add cart item"),
    CART_ITEM_NOT_FOUND("Cart Item not found with id "),
    CUSTOMER_NOT_FOUND("Customer not found with id "),
    Menu_ITEM_NOT_FOUND("Menu Item not found with id "),
    CART_NOT_FOUND("Cart not found with id "),
    INVALID_REQUEST("Invalid request data"),
    ;

    private final String message;

    SuccessAndErrorMessage(String message) {
        this.message = message;
    }

}
