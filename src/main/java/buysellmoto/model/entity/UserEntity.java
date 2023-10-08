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
@Table(name = "USER_INFO")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phone;

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long roleId;

}
