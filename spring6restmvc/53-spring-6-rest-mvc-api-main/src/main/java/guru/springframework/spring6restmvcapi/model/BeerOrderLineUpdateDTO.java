package guru.springframework.spring6restmvcapi.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrderLineUpdateDTO {


    private UUID id;

    @NotNull
    private UUID beerId;

    @Min(value = 1, message = "Quantity on hand must be greater than 0")
    private Integer orderQuantity;

    private Integer quantityAllocated;
}
