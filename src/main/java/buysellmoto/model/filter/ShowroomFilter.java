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
public class ShowroomFilter {

    //Dto
    private String name;

    private String address;

    private String province;

    private String email;

    private String phone;

}
