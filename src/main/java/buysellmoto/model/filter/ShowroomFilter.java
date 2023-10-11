package buysellmoto.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFilter {

    //Dto
    private String fullName;

    private LocalDateTime dob;

    private String address;

    private Long userId;

}
