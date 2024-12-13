package guru.springframework.spring6restmvc.model;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data

public class BeerOrderShipmentDTO {


    private UUID id;


    private Long version;

    @NotBlank
    private String trackingNumber;

    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
