package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.model.dto.RoleDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleFilter extends ApiFilter<RoleDto> {

    //Filter
    private List<Long> ids = new ArrayList<>();

}
