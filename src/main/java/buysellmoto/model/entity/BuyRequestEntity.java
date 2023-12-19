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
@Table(name = "BUY_REQUEST")
public class BuyRequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    private String status;

    private String cancelReason;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long customerId;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long showroomId;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long motorbikeId;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long postId;

}
