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
@Table(name = "TRANSACTION")
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long showroomStaffId;

    @Column(nullable = false)
    private String showroomStaffName;

    @Column(nullable = false)
    private String originBankName;

    @Column(nullable = false)
    private String originAccountNumber;

    @Column(nullable = false)
    private String originAccountHolder;

    @Column(nullable = false)
    private String targetBankName;

    @Column(nullable = false)
    private String targetAccountNumber;

    @Column(nullable = false)
    private String targetAccountHolder;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordedDate;

    private Long buyRequestId;

    private Long sellRequestId;

}