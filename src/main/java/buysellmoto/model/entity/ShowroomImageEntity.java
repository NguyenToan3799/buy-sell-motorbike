package buysellmoto.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SHOWROOM_IMAGE")
public class ShowroomImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

//    @Column(nullable = false)
//    private String fullName;
//
//    @Column(columnDefinition = "TIMESTAMP")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime dob;
//
//    private String address;
//
//    @Column(nullable = false, columnDefinition = "BIGINT")
//    private Long userId;
//
//    @Column(nullable = false, columnDefinition = "BIGINT")
//    private Long showroomId;

}
