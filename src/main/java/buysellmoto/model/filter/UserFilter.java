package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.dao.EmployeeShowroomDao;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserFilter extends ApiFilter<UserDto> {

    //Change password
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    //Other
    private CustomerDto customerDto;

    private EmployeeShowroomDto employeeShowroomDto;

}
