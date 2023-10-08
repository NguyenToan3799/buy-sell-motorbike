package buysellmoto.model.filter;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleFilter {

    //Dto
    private String name;

    //Filter
    private List<Long> ids = new ArrayList<>();

}
