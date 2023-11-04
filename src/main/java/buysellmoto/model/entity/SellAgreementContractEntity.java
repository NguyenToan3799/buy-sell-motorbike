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
@Table(name = "SELL_AGREEMENT_CONTRACT")
public class SellAgreementContractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime contractSignDate;

    @Column(nullable = false)
    private Double contractDuration;

    @Column(nullable = false)
    private Double serviceFee;

    @Column(nullable = false)
    private Double commissionFee;

    @Column(nullable = false)
    private Double agreementPrice;

    @Column(nullable = false)
    private Boolean customerSignature;

    @Column(nullable = false)
    private Boolean showroomSignature;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long customerId;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long showroomId;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long motorbikeId;

}
