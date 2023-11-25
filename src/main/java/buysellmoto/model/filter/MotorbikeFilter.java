package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.dto.MotorbikeImageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MotorbikeFilter extends ApiFilter<MotorbikeDto> {

    // For create
    private List<MotorbikeImageDto> motorbikeImageDtos = new ArrayList<>();

}
