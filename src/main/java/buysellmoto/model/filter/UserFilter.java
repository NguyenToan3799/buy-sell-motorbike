package buysellmoto.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

    //Dto
    private String userName;

    private String phone;

    private String email;

    private String password;

    private Boolean status;

    private Long roleId;

}
