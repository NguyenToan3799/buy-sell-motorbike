package buysellmoto.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CHECKED_SELL_REQUEST")
public class CheckedSellRequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime checkedDate;

    @Column(nullable = false)
    private Double finalPrice;

    private Double sellerReceiveAmount;

    private String technicianNote;

    private String checkedBy;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long sellRequestId;

}
