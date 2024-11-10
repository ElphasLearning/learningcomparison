package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrderShipmentUpdateDTO {

    @NotBlank
    private String trackingNumber;

}
