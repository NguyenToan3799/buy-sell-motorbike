package buysellmoto.model.filter.other;

import buysellmoto.model.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFilter {

    private String loginIdentity;

    private String password;

}
