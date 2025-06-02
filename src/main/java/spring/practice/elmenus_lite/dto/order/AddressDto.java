package spring.practice.elmenus_lite.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddressDto {
    private Long id;
    private String label;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String location;
    private String floor;
    private String apartment;
    private String additionalDirection;
}
