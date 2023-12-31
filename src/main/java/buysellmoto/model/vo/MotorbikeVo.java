package buysellmoto.model.vo;

import buysellmoto.model.dto.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotorbikeVo extends MotorbikeDto {

    private List<MotorbikeImageDto> motorbikeImageDtos = new ArrayList<>();

}
