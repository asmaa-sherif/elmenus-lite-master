package spring.practice.elmenus_lite.dto;

import lombok.*;
import spring.practice.elmenus_lite.enums.Gender;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
}
