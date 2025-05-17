package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDto {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
}
