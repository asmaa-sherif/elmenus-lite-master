package spring.practice.elmenus_lite.dto;

import lombok.Getter;
import lombok.Setter;
import spring.practice.elmenus_lite.enums.Gender;

@Getter
@Setter
public class CustomerDto {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
}
