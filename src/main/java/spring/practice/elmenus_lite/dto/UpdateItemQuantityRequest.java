package spring.practice.elmenus_lite.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemQuantityRequest {
    private Long cartItemId;
    private Integer quantity;
}
