package spring.practice.elmenus_lite.enums;

import lombok.Getter;

@Getter
public enum SuccessAndErrorMessage {
    // info
    INFO("Info"),

    // Success Messages
    SUCCESS("Success"),
    QUANTITY_UPDATED_SUCCESSFULLY("Quantity updated successfully"),

    // Error Messages
    ERROR("Error"),
    INVALID_QUANTITY("Invalid quantity"),
    INVALID_CART_ITEM_ID("Invalid cart item ID"),
    CAN_NOT_UPDATE_QUANTITY("can not update quantity"),
    CART_ITEM_NOT_FOUND("Cart Item not found with id "),
    INVALID_REQUEST("Invalid request data"),
    ;

    private final String message;

    SuccessAndErrorMessage(String message) {
        this.message = message;
    }

}
