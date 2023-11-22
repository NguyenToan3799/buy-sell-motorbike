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
@Table(name = "MOTORBIKE")
public class MotorbikeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String licensePlate;

    private Double engineSize;

    private String description;

    @Column(nullable = false)
    private String condition;

    @Column(nullable = false)
    private Double odo;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime yearOfRegistration;

    @Column(nullable = false)
    private String status;

//    @Column(nullable = false)
//    private Long motoTypeId;

    @Column(nullable = false)
    private String motoType;

    @Column(nullable = false)
    private Long motoBrandId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long showroomId;

}
