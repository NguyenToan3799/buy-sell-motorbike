package buysellmoto.model.vo;

import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo extends UserDto {

    private CustomerDto customerDto;
    private RoleDto roleDto;

}
